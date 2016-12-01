package com.memento.android.ui.splash;

import android.support.annotation.NonNull;

import com.crh.android.common.util.ActivityUtils;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public class SplashPresenter implements SplashContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private SplashContract.View mView;
    private int mWidth;

    public SplashPresenter(@NonNull SplashContract.View view, int width) {
        mView =  checkNotNull(view, "splashview cannot be null!");
        mWidth =  width;
        mSubscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadImageUrl();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadImageUrl() {
//        mDataManager.getImageList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<List<SplashImageEntity>, String>() {
//                    @Override
//                    public String call(List<SplashImageEntity> splashImageEntities) {
////                        int random  = new Random().nextInt(splashImageEntities.size());
////                        return splashImageEntities.get(random).
////                                getPhotoUrl(mWidth);
//                        return ActivityUtils.getQiniuImageUrl();
//                    }
//                })
//                .subscribe(new DefaultSubscriber<String>(){
//                    @Override
//                    public void onNext(String s) {
//                        super.onNext(s);
//                        mView.showImage(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mView.showError();
//                    }
//                });
        mView.showImage(ActivityUtils.getQiniuImageUrl());
    }
}
