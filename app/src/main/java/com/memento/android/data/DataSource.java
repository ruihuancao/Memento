package com.memento.android.data;

import com.memento.android.bean.DouBanMovieBean;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.bean.ZhihuDetailBean;
import com.memento.android.bean.ZhihuNewsBean;

import rx.Observable;


public interface DataSource {

    ///////////////////////////// 豆瓣/////////////////////////////////////

    Observable<DouBanMovieBean> getTop250Movie(int start, int count);

    Observable<DouBanMovieBean> getComingSoonMovie(int start, int count);

    Observable<DouBanMovieBean> getTheatersMovie(String city);

    ///////////////////////////////知乎////////////////////////////////////

    Observable<ZhihuNewsBean> getArticleList(String date);

    Observable<ZhihuNewsBean> getNewArticleList();

    Observable<ZhihuDetailBean> getArticleDetail(String id);

    ////////////////////////////leancloud///////////////////////////////////////
    Observable<LeanCloudUserBean> register(LeanCloudUserBean user);

    Observable<LeanCloudUserBean> getUser(String session, String objectId);

    Observable<LeanCloudUserBean> updateUser(String session, String objectId, LeanCloudUserBean user);

    Observable<LeanCloudUserBean> login(String name, String password);

    Observable<LeanCloudUserBean> login(LeanCloudUserBean user);

    Observable<LeanCloudUserBean> getCurrentUser(String session);

    Observable<LeanCloudUserBean> requestEmailVerify(String email);

    Observable<LeanCloudUserBean> requestPasswordReset(String email);
}
