package com.memento.android.ui.base;


import com.memento.android.data.Repository;

public class BasePresenter<T>{

    private T mView;

    protected final Repository mRepository;

    public BasePresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }

    public void attachView(T view) {
        mView = view;
    }

    public T getView() {
        return mView;
    }

}

