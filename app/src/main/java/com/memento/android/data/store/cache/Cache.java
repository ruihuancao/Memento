package com.memento.android.data.store.cache;


public interface Cache {

    void clearCache(String key);
    void clearAllCache();
    void putCache(String key, String data);
    String getCache(String key);
    boolean isCached(String key);
    boolean isExpired(String key);
}
