package com.memento.android.ui.base;


import rx.Subscription;

public class BasePresenter<T>{

    private T mView;

    protected Subscription mSubscription;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

}

