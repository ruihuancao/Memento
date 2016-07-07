package com.memento.android.data.subscriber;

import com.orhanobut.logger.Logger;

public class DefaultSubscriber<T> extends rx.Subscriber<T> {
    @Override public void onCompleted() {
        Logger.d("load onCompleted");
    }

    @Override public void onError(Throwable e) {
        e.printStackTrace();
        Logger.d("load onError");
    }

    @Override public void onNext(T t) {
        Logger.d("load onNext");
    }
}