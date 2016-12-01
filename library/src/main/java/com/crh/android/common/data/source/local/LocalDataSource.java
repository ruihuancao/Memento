package com.crh.android.common.data.source.local;

import android.text.TextUtils;

import com.crh.android.common.data.DataSource;
import com.crh.android.common.data.LocalKey;
import com.crh.android.common.data.exception.ApiException;
import com.crh.android.common.data.source.entity.DouBanMovieEntity;
import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.crh.android.common.data.source.entity.ZhihuDetailEntity;
import com.crh.android.common.data.source.entity.ZhihuNewsEntity;
import com.crh.android.common.data.source.local.cache.Cache;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by android on 16-11-22.
 */

public class LocalDataSource implements DataSource{

    private static LocalDataSource INSTANCE;

    private Cache mCache;
    private Gson mGson;

    public LocalDataSource(Cache cache, Gson gson){
        mCache = cache;
        mGson = gson;
    }

    public static LocalDataSource getInstance(Cache cache, Gson gson) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(cache, gson);
        }
        return INSTANCE;
    }

    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieEntity>() {
            @Override
            public void call(Subscriber<? super DouBanMovieEntity> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieEntity.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieEntity>() {
            @Override
            public void call(Subscriber<? super DouBanMovieEntity> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieEntity.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(city)
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieEntity>() {
            @Override
            public void call(Subscriber<? super DouBanMovieEntity> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieEntity.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<ZhihuNewsEntity> getArticleList(String date) {
        return null;
    }

    @Override
    public Observable<ZhihuNewsEntity> getNewArticleList() {
        return null;
    }

    @Override
    public Observable<ZhihuDetailEntity> getArticleDetail(String id) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> register(LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> getUser(String session, String objectId) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> updateUser(String session, String objectId, LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> login(String name, String password) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> login(LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> getCurrentUser(String session) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> requestEmailVerify(String email) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserEntiry> requestPasswordReset(String email) {
        return null;
    }
}
