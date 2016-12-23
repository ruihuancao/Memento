package com.memento.android.data.source.remote.service;

import com.memento.android.bean.ZhihuDetailBean;
import com.memento.android.bean.ZhihuNewsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface ZhihuApiService {

    String API_BASE_URL = "http://news-at.zhihu.com/api/4/";

    @GET("news/before/{date}")
    Observable<ZhihuNewsBean> getArticleList(@Path("date") String date);

    @GET("news/latest")
    Observable<ZhihuNewsBean> getNewArticleList();

    @GET("news/{id}")
    Observable<ZhihuDetailBean> getArticleDetail(@Path("id") String id);
}
