package com.memento.android.ui.zhihu.splash;

import android.support.annotation.NonNull;

import com.crh.android.common.data.DataRepository;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public class ZhihuSplashPresenter implements ZhihuSplashContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private DataRepository mDataRepository;
    private ZhihuSplashContract.View mView;
    private int mWidth;

    public ZhihuSplashPresenter(@NonNull DataRepository dataRepository, @NonNull ZhihuSplashContract.View view, int width) {
        mDataRepository = checkNotNull(dataRepository, "dataRepository cannot be null");
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
//        mDataRepository.getImageList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<List<SplashImageEntity>, String>() {
//                    @Override
//                    public String call(List<SplashImageEntity> splashImageEntities) {
//                        int random  = new Random().nextInt(splashImageEntities.size());
//                        return splashImageEntities.get(random).
//                                getPhotoUrl(mWidth);
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

    }
}
