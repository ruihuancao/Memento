package com.memento.android.data.source.local;

import android.text.TextUtils;

import com.memento.android.data.DataSource;
import com.memento.android.data.LocalKey;
import com.memento.android.data.exception.ApiException;
import com.memento.android.bean.DouBanMovieBean;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.bean.ZhihuDetailBean;
import com.memento.android.bean.ZhihuNewsBean;
import com.memento.android.data.source.local.cache.Cache;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by android on 16-11-22.
 */

public class LocalDataSource implements DataSource{

    private Cache mCache;
    private Gson mGson;

    public LocalDataSource(Cache cache, Gson gson){
        mCache = cache;
        mGson = gson;
    }

    @Override
    public Observable<DouBanMovieBean> getTop250Movie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieBean>() {
            @Override
            public void call(Subscriber<? super DouBanMovieBean> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieBean.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<DouBanMovieBean> getComingSoonMovie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieBean>() {
            @Override
            public void call(Subscriber<? super DouBanMovieBean> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieBean.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<DouBanMovieBean> getTheatersMovie(String city) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(city)
                .generator();
        return Observable.create(new Observable.OnSubscribe<DouBanMovieBean>() {
            @Override
            public void call(Subscriber<? super DouBanMovieBean> subscriber) {
                String data = mCache.get(key);
                if(TextUtils.isEmpty(data)){
                    Logger.d("cache data not found");
                    subscriber.onError(new ApiException("data not found"));
                }else{
                    Logger.d("cache data found");
                    subscriber.onNext(mGson.fromJson(data, DouBanMovieBean.class));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<ZhihuNewsBean> getArticleList(String date) {
        return null;
    }

    @Override
    public Observable<ZhihuNewsBean> getNewArticleList() {
        return null;
    }

    @Override
    public Observable<ZhihuDetailBean> getArticleDetail(String id) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> register(LeanCloudUserBean user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> getUser(String session, String objectId) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> updateUser(String session, String objectId, LeanCloudUserBean user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> login(String name, String password) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> login(LeanCloudUserBean user) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> getCurrentUser(String session) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> requestEmailVerify(String email) {
        return null;
    }

    @Override
    public Observable<LeanCloudUserBean> requestPasswordReset(String email) {
        return null;
    }
}
