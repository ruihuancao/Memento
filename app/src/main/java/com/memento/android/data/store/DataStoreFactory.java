package com.memento.android.data.store;

import android.support.annotation.NonNull;

import com.memento.android.data.store.cache.Cache;
import com.memento.android.data.store.http.HttpHelper;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/10.
 */

@Singleton
public class DataStoreFactory {

    private Cache mCache;
    private HttpHelper mHttpHelper;

    @Inject
    public DataStoreFactory(@NonNull Cache mCache, @NonNull HttpHelper httpHelper) {
        this.mCache = checkNotNull(mCache);
        this.mHttpHelper = checkNotNull(httpHelper);
    }

    /**
     *
     * @param key
     * @return
     */
    public DataStore create(String key){
        Logger.d(key);
        DataStore dataStore;
        if(mCache.isCached(key) && !mCache.isExpired(key)){
            dataStore = new DiskDataStore(mCache);
        }else{
            dataStore = new CloudDataStore(mHttpHelper, mCache);
        }
        return dataStore;
    }

    /**
     *
     * @return
     */
    public DataStore create(){
        return new CloudDataStore(mHttpHelper, mCache);
    }
}
