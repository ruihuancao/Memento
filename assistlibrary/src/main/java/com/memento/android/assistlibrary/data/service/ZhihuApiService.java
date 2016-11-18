package com.memento.android.assistlibrary.data.service;

import com.memento.android.assistlibrary.data.entity.ZhihuDetailEntity;
import com.memento.android.assistlibrary.data.entity.ZhihuNewsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by caoruihuan on 16/9/28.
 */

public interface ZhihuApiService {

    String API_BASE_URL = "http://news-at.zhihu.com/api/4/";

    @GET("news/before/{date}")
    Observable<ZhihuNewsEntity> getArticleList(@Path("date") String date);

    @GET("news/latest")
    Observable<ZhihuNewsEntity> getNewArticleList();

    @GET("news/{id}")
    Observable<ZhihuDetailEntity> getArticleDetail(@Path("id") String id);
}
