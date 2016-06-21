package com.memento.android.data.repository.datasource;

import com.memento.android.data.cache.Cache;
import com.memento.android.data.remote.APIService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataStoreFactory {

    private final Cache cache;
    private final APIService mAPIService;
    private final JsonSerializer jsonSerializer;

    @Inject
    public DataStoreFactory(APIService apiService, Cache cache, JsonSerializer jsonSerializer) {
        this.cache = cache;
        this.mAPIService = apiService;
        this.jsonSerializer = jsonSerializer;
    }

    public DataStore create(){
        return create(null);
    }

    public DataStore create(String cacheKey){
        DataStore dataStore;
        if(cacheKey == null){
            dataStore = new CloudDataStore(mAPIService);
        }else if(this.cache.isCached(cacheKey) && !this.cache.isExpired(cacheKey)) {
            dataStore = new DiskDataStore(this.cache, this.jsonSerializer);
        } else {
            dataStore = new CloudDataStore(this.mAPIService, this.cache, this.jsonSerializer);
        }
        return dataStore;
    }

}
