package com.memento.android.data.source.real;

import android.support.annotation.NonNull;

import com.memento.android.data.DataSource;
import com.memento.android.data.LocalKey;
import com.memento.android.bean.DouBanMovieBean;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.bean.ZhihuDetailBean;
import com.memento.android.bean.ZhihuNewsBean;
import com.memento.android.data.source.local.cache.Cache;
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
    public Observable<DouBanMovieBean> getTop250Movie(final int start, final int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_TOP_250_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return getDataSource(key).getTop250Movie(start, count)
                .doOnNext(new Action1<DouBanMovieBean>() {
                    @Override
                    public void call(DouBanMovieBean douBanMovieBean) {
                        Logger.d("to save cache");
                        mCache.put(key, mGson.toJson(douBanMovieBean));
                    }
                });
    }

    @Override
    public Observable<DouBanMovieBean> getComingSoonMovie(int start, int count) {
        final String key = new LocalKey(LocalKey.DOUBAN_COMING_SOON_CACHE)
                .addParam(String.valueOf(start))
                .addParam(String.valueOf(count))
                .generator();
        return mRemoteDataSource.getComingSoonMovie(start, count)
                .doOnNext(new Action1<DouBanMovieBean>() {
                    @Override
                    public void call(DouBanMovieBean douBanMovieBean) {
                        mCache.put(key, mGson.toJson(douBanMovieBean));
                    }
                });
    }

    @Override
    public Observable<DouBanMovieBean> getTheatersMovie(String city) {
        final String key = new LocalKey(LocalKey.DOUBAN_THEATERS_CACHE)
                .addParam(city)
                .generator();
        return mRemoteDataSource.getTheatersMovie(city)
                .doOnNext(new Action1<DouBanMovieBean>() {
                    @Override
                    public void call(DouBanMovieBean douBanMovieBean) {
                        mCache.put(key, mGson.toJson(douBanMovieBean));
                    }
                });
    }


    @Override
    public Observable<ZhihuNewsBean> getArticleList(String date) {
        return mRemoteDataSource.getArticleList(date);
    }

    @Override
    public Observable<ZhihuNewsBean> getNewArticleList() {
        return mRemoteDataSource.getNewArticleList();
    }

    @Override
    public Observable<ZhihuDetailBean> getArticleDetail(String id) {
        return mRemoteDataSource.getArticleDetail(id);
    }

    @Override
    public Observable<LeanCloudUserBean> register(LeanCloudUserBean user) {
        return mRemoteDataSource.register(user);
    }

    @Override
    public Observable<LeanCloudUserBean> getUser(String session, String objectId) {
        return mRemoteDataSource.getUser(session, objectId);
    }

    @Override
    public Observable<LeanCloudUserBean> updateUser(String session, String objectId, LeanCloudUserBean user) {
        return mRemoteDataSource.updateUser(session, objectId, user);
    }

    @Override
    public Observable<LeanCloudUserBean> login(String name, String password) {
        return mRemoteDataSource.login(name, password);
    }

    @Override
    public Observable<LeanCloudUserBean> login(LeanCloudUserBean user) {
        return mRemoteDataSource.login(user);
    }

    @Override
    public Observable<LeanCloudUserBean> getCurrentUser(String session) {
        return mRemoteDataSource.getCurrentUser(session);
    }

    @Override
    public Observable<LeanCloudUserBean> requestEmailVerify(String email) {
        return mRemoteDataSource.requestEmailVerify(email);
    }

    @Override
    public Observable<LeanCloudUserBean> requestPasswordReset(String email) {
        return mRemoteDataSource.requestPasswordReset(email);
    }
}
