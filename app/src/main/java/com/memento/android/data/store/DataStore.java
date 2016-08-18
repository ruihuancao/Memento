package com.memento.android.data.store;

import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by 曹瑞环 on 2016/8/10.
 */
public interface DataStore {

    Observable<List<SplashImageEntity>> getImageList();

    Observable<ZhihuArticleEntity> getArticleList(String date);

    Observable<ZhihuArticleEntity> getNewArticleList();

    Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id);

    Observable<DouBanMovieEntity> getTheatersMovie(String city);

    Observable<DouBanMovieEntity> getTop250Movie(int start, int count);

    Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count);
}
