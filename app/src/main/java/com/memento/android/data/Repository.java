package com.memento.android.data;


import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.IpAddressEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;

import java.util.List;

import rx.Observable;


public interface Repository {

    Observable<List<SplashImageEntity>> getImageList();

    Observable<ZhihuArticleEntity> getNewArticle(String date);

    Observable<ZhihuArticleEntity> getNewArticle();

    Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id);

    Observable<IpAddressEntity> getAddress(String ip);

    Observable<DouBanMovieEntity> getTheatersMovie(String city);

    Observable<DouBanMovieEntity> getTop250Movie(int start, int count);

    Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count);
}
