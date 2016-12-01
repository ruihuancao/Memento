package com.crh.android.common.data;

import com.crh.android.common.data.source.entity.DouBanMovieEntity;
import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.crh.android.common.data.source.entity.ZhihuDetailEntity;
import com.crh.android.common.data.source.entity.ZhihuNewsEntity;

import rx.Observable;

/**
 * Created by android on 16-11-21.
 */

public interface DataSource {

    ///////////////////////////// 豆瓣/////////////////////////////////////

    Observable<DouBanMovieEntity> getTop250Movie(int start, int count);

    Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count);

    Observable<DouBanMovieEntity> getTheatersMovie(String city);

    ///////////////////////////////知乎////////////////////////////////////

    Observable<ZhihuNewsEntity> getArticleList(String date);

    Observable<ZhihuNewsEntity> getNewArticleList();

    Observable<ZhihuDetailEntity> getArticleDetail(String id);

    ////////////////////////////leancloud///////////////////////////////////////
    Observable<LeanCloudUserEntiry> register(LeanCloudUserEntiry user);

    Observable<LeanCloudUserEntiry> getUser(String session, String objectId);

    Observable<LeanCloudUserEntiry> updateUser(String session, String objectId, LeanCloudUserEntiry user);

    Observable<LeanCloudUserEntiry> login(String name,String password);

    Observable<LeanCloudUserEntiry> login(LeanCloudUserEntiry user);

    Observable<LeanCloudUserEntiry> getCurrentUser(String session);

    Observable<LeanCloudUserEntiry> requestEmailVerify(String email);

    Observable<LeanCloudUserEntiry> requestPasswordReset(String email);
}
