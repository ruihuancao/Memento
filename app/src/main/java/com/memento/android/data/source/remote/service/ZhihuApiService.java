package com.memento.android.data.source.remote.service;

import com.memento.android.data.source.entity.ZhihuDetailEntity;
import com.memento.android.data.source.entity.ZhihuNewsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface ZhihuApiService {

    String API_BASE_URL = "http://news-at.zhihu.com/api/4/";

    @GET("news/before/{date}")
    Observable<ZhihuNewsEntity> getArticleList(@Path("date") String date);

    @GET("news/latest")
    Observable<ZhihuNewsEntity> getNewArticleList();

    @GET("news/{id}")
    Observable<ZhihuDetailEntity> getArticleDetail(@Path("id") String id);
}
