package com.memento.android.data.source.remote;


import com.google.gson.Gson;
import com.memento.android.data.DataHelper;
import com.memento.android.data.DataSource;
import com.memento.android.bean.DouBanMovieBean;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.bean.ZhihuDetailBean;
import com.memento.android.bean.ZhihuNewsBean;
import com.memento.android.data.source.remote.service.DoubanService;
import com.memento.android.data.source.remote.service.DxyerApiService;
import com.memento.android.data.source.remote.service.LeanCloudApiService;
import com.memento.android.data.source.remote.service.TulingApiService;
import com.memento.android.data.source.remote.service.ZhihuApiService;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.functions.Action1;


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
    public Observable<DouBanMovieBean> getTop250Movie(int start, int count) {
        return mDoubanService.getTop250Movie(start, count)
                .doOnNext(new Action1<DouBanMovieBean>() {
                    @Override
                    public void call(DouBanMovieBean douBanMovieBean) {
                        String data = mGson.toJson(douBanMovieBean);

                    }
                });
    }

    @Override
    public Observable<DouBanMovieBean> getComingSoonMovie(int start, int count) {
        return mDoubanService.getComingSoonMovie(start, count);
    }

    @Override
    public Observable<DouBanMovieBean> getTheatersMovie(String city) {
        return mDoubanService.getTheatersMovie(city);
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

    @Override
    public Observable<ZhihuNewsBean> getArticleList(String date) {
        return mZhihuApiService.getArticleList(date);
    }

    @Override
    public Observable<ZhihuNewsBean> getNewArticleList() {
        return mZhihuApiService.getNewArticleList();
    }

    @Override
    public Observable<ZhihuDetailBean> getArticleDetail(String id) {
        return mZhihuApiService.getArticleDetail(id);
    }
}
