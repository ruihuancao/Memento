package com.memento.android.data.store;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.memento.android.data.CacheKey;
import com.memento.android.data.KeyGenerator;
import com.memento.android.data.store.cache.Cache;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.exception.ApiException;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 曹瑞环 on 2016/8/10.
 */
public class DiskDataStore implements DataStore{

    private final Cache mCache;
    private Gson mGson;

    public DiskDataStore(Cache mCache) {
        this.mCache = mCache;
        this.mGson = new Gson();
    }

    @Override
    public Observable<List<SplashImageEntity>> getImageList() {
        return Observable.create(new Observable.OnSubscribe<List<SplashImageEntity>>() {
            @Override
            public void call(Subscriber<? super List<SplashImageEntity>> subscriber) {
                String data = mCache.getCache(KeyGenerator.generator(CacheKey.KEY_IMAGE_LIST));
                if(TextUtils.isEmpty(data)){
                    subscriber.onError(new ApiException());
                }else{
                    List<SplashImageEntity> list = mGson.fromJson(data,
                            new TypeToken<List<SplashImageEntity>>() {}.getType());
                    subscriber.onNext(list);
                }
            }
        });
    }

    @Override
    public Observable<ZhihuArticleEntity> getArticleList(final String date) {
        return Observable.create(new Observable.OnSubscribe<ZhihuArticleEntity>() {
            @Override
            public void call(Subscriber<? super ZhihuArticleEntity> subscriber) {
                String data = mCache.getCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_NEWS, date));
                if(TextUtils.isEmpty(data)){
                    subscriber.onError(new ApiException());
                }else{
                    subscriber.onNext(mGson.fromJson(data, ZhihuArticleEntity.class));
                }
            }
        });
    }

    @Override
    public Observable<ZhihuArticleEntity> getNewArticleList() {
        return Observable.create(new Observable.OnSubscribe<ZhihuArticleEntity>() {
            @Override
            public void call(Subscriber<? super ZhihuArticleEntity> subscriber) {
                String data = mCache.getCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_NEWS));
                if(TextUtils.isEmpty(data)){
                    subscriber.onError(new ApiException());
                }else{
                    subscriber.onNext(mGson.fromJson(data, ZhihuArticleEntity.class));
                }
            }
        });
    }

    @Override
    public Observable<ZhihuArticleDetailEmtity> getArticleDetail(final String id) {
        return Observable.create(new Observable.OnSubscribe<ZhihuArticleDetailEmtity>() {
            @Override
            public void call(Subscriber<? super ZhihuArticleDetailEmtity> subscriber) {
                String data = mCache.getCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_DETAIL, id));
                if(TextUtils.isEmpty(data)){
                    subscriber.onError(new ApiException());
                }else{
                    subscriber.onNext(mGson.fromJson(data, ZhihuArticleDetailEmtity.class));
                }
            }
        });
    }

    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        return null;
    }

    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return null;
    }

    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return null;
    }
}
