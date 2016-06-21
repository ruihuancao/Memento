package com.memento.android.injection.module;

import android.app.Application;
import android.content.Context;

import com.memento.android.data.Repository;
import com.memento.android.data.cache.Cache;
import com.memento.android.data.cache.CacheImpl;
import com.memento.android.data.remote.APIService;
import com.memento.android.data.repository.DataRepository;
import com.memento.android.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    APIService provideAPIService(){
        return APIService.Factory.createService(mApplication);
    }

    @Provides
    @Singleton
    Cache provideCache(CacheImpl cache){
        return cache;
    }

    @Provides
    @Singleton
    Repository provideRepository(DataRepository dataRepository){
        return dataRepository;
    }


}
