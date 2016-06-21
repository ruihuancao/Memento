package com.memento.android.data.cache;

import rx.Observable;


public interface Cache {

    Observable<String> get(final String key);

    void put(final String key, String content);

    boolean isCached(final String key);

    boolean isExpired(String key);

    void clear(String key);
}
