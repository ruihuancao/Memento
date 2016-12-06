package com.crh.android.common.data.source.real;

import android.support.annotation.NonNull;

import com.crh.android.common.data.DataSource;
import com.crh.android.common.data.LocalKey;
import com.crh.android.common.data.source.entity.DouBanMovieEntity;
import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.crh.android.common.data.source.entity.ZhihuDetailEntity;
import com.crh.android.common.data.source.entity.ZhihuNewsEntity;
import com.crh.android.common.data.source.local.cache.Cache;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.functions.Action1;


public class RealDataSource implements DataSource {

    @NonNull
    private final DataSource mRemoteDataSource;
    @NonNull
    private final DataSource mLocalDataSource;
    @NonNull
    private final Cache mCache;
    @NonNull
    private final Gson mGson;

    public RealDataSource(DataSource localDataSource, DataSource remoteDataSource, Cache cache, Gson gson){
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
        this.mCache = cache;
        this.mGson = gson;
    }

    private DataSource getDataSource(String key){
        if(mCache != null && mCache.exist(key)){
            Logger.d("local data source, key: %s", key);
            return mLocalDataSource;
        }else{
            Logger.d("remote data source");
            return mRemoteDataSource;
        }
    }

    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(final int start, final int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return getDataSource(key).getTop250Movie(start, count)
                .doOnNext(new Action1<DouBanMovieEntity>() {
                    @Override
                    public void call(DouBanMovieEntity douBanMovieEntity) {
                        Logger.d("to save cache");
                        mCache.put(key, mGson.toJson(douBanMovieEntity));
                    }
                });
    }

    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_COMING_SOON_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return mRemoteDataSource.getComingSoonMovie(start, count)
                .doOnNext(new Action1<DouBanMovieEntity>() {
                    @Override
                    public void call(DouBanMovieEntity douBanMovieEntity) {
                        mCache.put(key, mGson.toJson(douBanMovieEntity));
                    }
                });
    }

    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        final String key = new LocalKey(LocalKey.DOUBAN_THEATERS_CACHE)
                .addParam(city)
                .generator();
        return mRemoteDataSource.getTheatersMovie(city)
                .doOnNext(new Action1<DouBanMovieEntity>() {
                    @Override
                    public void call(DouBanMovieEntity douBanMovieEntity) {
                        mCache.put(key, mGson.toJson(douBanMovieEntity));
                    }
                });
    }


    @Override
    public Observable<ZhihuNewsEntity> getArticleList(String date) {
        return mRemoteDataSource.getArticleList(date);
    }

    @Override
    public Observable<ZhihuNewsEntity> getNewArticleList() {
        return mRemoteDataSource.getNewArticleList();
    }

    @Override
    public Observable<ZhihuDetailEntity> getArticleDetail(String id) {
        return mRemoteDataSource.getArticleDetail(id);
    }

    @Override
    public Observable<LeanCloudUserEntiry> register(LeanCloudUserEntiry user) {
        return mRemoteDataSource.register(user);
    }

    @Override
    public Observable<LeanCloudUserEntiry> getUser(String session, String objectId) {
        return mRemoteDataSource.getUser(session, objectId);
    }

    @Override
    public Observable<LeanCloudUserEntiry> updateUser(String session, String objectId, LeanCloudUserEntiry user) {
        return mRemoteDataSource.updateUser(session, objectId, user);
    }

    @Override
    public Observable<LeanCloudUserEntiry> login(String name, String password) {
        return mRemoteDataSource.login(name, password);
    }

    @Override
    public Observable<LeanCloudUserEntiry> login(LeanCloudUserEntiry user) {
        return mRemoteDataSource.login(user);
    }

    @Override
    public Observable<LeanCloudUserEntiry> getCurrentUser(String session) {
        return mRemoteDataSource.getCurrentUser(session);
    }

    @Override
    public Observable<LeanCloudUserEntiry> requestEmailVerify(String email) {
        return mRemoteDataSource.requestEmailVerify(email);
    }

    @Override
    public Observable<LeanCloudUserEntiry> requestPasswordReset(String email) {
        return mRemoteDataSource.requestPasswordReset(email);
    }
}
