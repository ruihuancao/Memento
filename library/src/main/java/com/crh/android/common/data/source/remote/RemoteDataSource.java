package com.crh.android.common.data.source.remote;

import com.crh.android.common.data.DataSource;
import com.crh.android.common.data.DataHelper;
import com.crh.android.common.data.source.entity.DouBanMovieEntity;
import com.crh.android.common.data.source.remote.service.DoubanService;
import com.crh.android.common.data.source.remote.service.DxyerApiService;
import com.crh.android.common.data.source.remote.service.LeanCloudApiService;
import com.crh.android.common.data.source.remote.service.TulingApiService;
import com.crh.android.common.data.source.remote.service.ZhihuApiService;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.functions.Action1;


/**
 * Created by android on 16-11-21.
 */

public class RemoteDataSource implements DataSource {

    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    private ZhihuApiService mZhihuApiService;
    private DxyerApiService mDxyerApiService;
    private TulingApiService mTulingApiService;
    private LeanCloudApiService mLeanCloudApiService;
    private DoubanService mDoubanService;

    public RemoteDataSource(Gson gson, OkHttpClient okHttpClient) {
        this.mGson = gson;
        this.mOkHttpClient = okHttpClient;
        this.mZhihuApiService = DataHelper.createService(ZhihuApiService.API_BASE_URL, mOkHttpClient, ZhihuApiService.class);
        this.mDoubanService = DataHelper.createService(DoubanService.API_BASE_URL, mOkHttpClient, DoubanService.class);
        this.mDxyerApiService = DataHelper.createService(DxyerApiService.API_BASE_URL, mOkHttpClient, DxyerApiService.class);
        this.mTulingApiService = DataHelper.createService(TulingApiService.API_BASE_URL, mOkHttpClient, TulingApiService.class);
        this.mLeanCloudApiService = DataHelper.createService(LeanCloudApiService.API_BASE_URL, mOkHttpClient, LeanCloudApiService.class);
    }

    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return mDoubanService.getTop250Movie(start, count)
                .doOnNext(new Action1<DouBanMovieEntity>() {
                    @Override
                    public void call(DouBanMovieEntity douBanMovieEntity) {
                        String data = mGson.toJson(douBanMovieEntity);

                    }
                });
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return mDoubanService.getComingSoonMovie(start, count);
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.DouBanMovieEntity> getTheatersMovie(String city) {
        return mDoubanService.getTheatersMovie(city);
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> register(com.crh.android.common.data.source.entity.LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> getUser(String session, String objectId) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> updateUser(String session, String objectId, com.crh.android.common.data.source.entity.LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> login(String name, String password) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> login(com.crh.android.common.data.source.entity.LeanCloudUserEntiry user) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> getCurrentUser(String session) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> requestEmailVerify(String email) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.LeanCloudUserEntiry> requestPasswordReset(String email) {
        return null;
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.ZhihuNewsEntity> getArticleList(String date) {
        return mZhihuApiService.getArticleList(date);
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.ZhihuNewsEntity> getNewArticleList() {
        return mZhihuApiService.getNewArticleList();
    }

    @Override
    public Observable<com.crh.android.common.data.source.entity.ZhihuDetailEntity> getArticleDetail(String id) {
        return mZhihuApiService.getArticleDetail(id);
    }
}
