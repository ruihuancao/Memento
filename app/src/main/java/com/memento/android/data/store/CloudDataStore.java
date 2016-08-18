package com.memento.android.data.store;

import com.google.gson.Gson;
import com.memento.android.data.CacheKey;
import com.memento.android.data.KeyGenerator;
import com.memento.android.data.store.cache.Cache;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.store.http.HttpHelper;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;


public class CloudDataStore implements DataStore{

    private final HttpHelper mHttpHelper;
    private Cache mCache;

    public CloudDataStore(HttpHelper httpHelper, Cache cache) {
        this.mHttpHelper = httpHelper;
        this.mCache = cache;
    }


    @Override
    public Observable<List<SplashImageEntity>> getImageList() {
        return mHttpHelper.getmSystemApiService().getSplash();
    }

    @Override
    public Observable<ZhihuArticleEntity> getArticleList(final String date) {
        return mHttpHelper.getmSystemApiService()
                .getArticleList(date)
                .doOnNext(new Action1<ZhihuArticleEntity>() {
                    @Override
                    public void call(ZhihuArticleEntity zhihuArticleEntity) {
                        mCache.putCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_DATE, date),
                                new Gson().toJson(zhihuArticleEntity));
                    }
                });
    }

    @Override
    public Observable<ZhihuArticleEntity> getNewArticleList() {
        return mHttpHelper.getmSystemApiService()
                .getNewArticleList()
                .doOnNext(new Action1<ZhihuArticleEntity>() {
                    @Override
                    public void call(ZhihuArticleEntity zhihuArticleEntity) {
                        mCache.putCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_NEWS),
                                new Gson().toJson(zhihuArticleEntity));
                    }
                });
    }

    @Override
    public Observable<ZhihuArticleDetailEmtity> getArticleDetail(final String id) {
        return mHttpHelper.getmSystemApiService()
                .getArticleDetail(id)
                .doOnNext(new Action1<ZhihuArticleDetailEmtity>() {
                    @Override
                    public void call(ZhihuArticleDetailEmtity zhihuArticleDetailEmtity) {
                        mCache.putCache(KeyGenerator.generator(CacheKey.KEY_ZHIHU_DETAIL, id),
                                new Gson().toJson(zhihuArticleDetailEmtity));
                    }
                });
    }

    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        return mHttpHelper.getmSystemApiService().getTheatersMovie(city);
    }

    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return mHttpHelper.getmSystemApiService().getTop250Movie(start, count);
    }

    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return mHttpHelper.getmSystemApiService().getComingSoonMovie(start, count);
    }
}
