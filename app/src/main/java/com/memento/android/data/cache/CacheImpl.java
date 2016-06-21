package com.memento.android.data.cache;

import android.content.Context;

import com.memento.android.injection.ApplicationContext;
import com.memento.android.util.AppUtils;
import com.orhanobut.logger.Logger;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by android on 16-5-26.
 */

@Singleton
public class CacheImpl implements Cache {

    private static final String SETTINGS_FILE_NAME = "com.memento.android.SETTINGS";
    private static final String DEFAULT_FILE_NAME = "cache_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final FileManager fileManager;
    private final Context context;
    private final File cacheDir;

    @Inject
    public CacheImpl(@ApplicationContext Context context, FileManager fileManager){
        this.context = context;
        this.fileManager = fileManager;
        this.cacheDir = this.context.getCacheDir();
    }

    @Override
    public Observable<String> get(final String key) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File cacheFile = buildFile(key);
                String content = fileManager.readFileContent(cacheFile);
                subscriber.onNext(content);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void put(String key, String content) {
        Logger.d("add cache:"+content);
        cacheWrite(key, content)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        setLastCacheUpdateTimeMillis(s);
                    }
                });
    }

    @Override
    public boolean isCached(String key) {
        File cacheFile = this.buildFile(key);
        return fileManager.exists(cacheFile);
    }

    @Override
    public boolean isExpired(String key) {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis(key);
        if(!AppUtils.isNetWorkAvailable(context)){
            lastUpdateTime = currentTime;
        }
        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);
        if(expired){
            clearCache(key);
        }
        return expired;
    }

    @Override
    public void clear(String key) {
        clearCache(key)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                    }
                });
    }

    private void setLastCacheUpdateTimeMillis(String key) {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                key, currentMillis);
    }

    private long getLastCacheUpdateTimeMillis(String key) {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                key);
    }

    private Observable<String> cacheWrite(final String key, final String content){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File cacheFile = CacheImpl.this.buildFile(key);
                fileManager.writeToFile(cacheFile, content);
                subscriber.onNext(key);
                subscriber.onCompleted();
                Logger.d("add cache to file success");
            }
        });
    }

    private Observable<String> clearCache(final String key){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File cacheFile = CacheImpl.this.buildFile(key);
                fileManager.clearFile(cacheFile);
                subscriber.onNext(key);
                subscriber.onCompleted();
            }
        });
    }

    private File buildFile(String key) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(key);
        return new File(fileNameBuilder.toString());
    }
}
