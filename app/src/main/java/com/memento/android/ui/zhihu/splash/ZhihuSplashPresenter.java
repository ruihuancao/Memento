package com.memento.android.ui.zhihu.splash;

import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.data.Repository;
import com.memento.android.data.model.LauncherImage;
import com.memento.android.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZhihuSplashPresenter extends BasePresenter<ZhihuSplashContract.View> implements ZhihuSplashContract.UserActionsListener {

    private final Repository mRepository;


    @Inject
    public ZhihuSplashPresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void getImage(String deviceInfo) {
        mRepository.getLauncherImage(deviceInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<LauncherImage>(){
                    @Override
                    public void onNext(LauncherImage launcherImage) {
                        super.onNext(launcherImage);
                        getView().showImage(launcherImage.getImageUrl());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().showError();
                    }
                });
    }
}