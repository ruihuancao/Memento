package com.memento.android.injection.module;

import android.app.Application;
import android.content.Context;

import com.memento.android.data.store.cache.Cache;
import com.memento.android.data.store.cache.CacheImpl;
import com.memento.android.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


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
    Cache provideCache(CacheImpl cacheImpl){
        return cacheImpl;
    }
}
