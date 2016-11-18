package com.memento.android.data;


import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by caoruihuan on 16/9/27.
 */

public interface DataInterface {

    Observable<List<SplashImageEntity>> getImageList();

    Observable<ZhihuArticleEntity> getNewArticle(String date);

    Observable<ZhihuArticleEntity> getNewArticle();

    Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id);

    Observable<DouBanMovieEntity> getTheatersMovie(String city);

    Observable<DouBanMovieEntity> getTop250Movie(int start, int count);

    Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count);
}
