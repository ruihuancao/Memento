package com.memento.android.data.subscriber;

public class DefaultSubscriber<T> extends rx.Subscriber<T> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
    }

    @Override public void onNext(T t) {
    }
}