package com.memento.android.data.source.remote.service;

import com.memento.android.bean.DouBanMovieBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by caoruihuan on 16/9/28.
 */

public interface DoubanService {
    String API_BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Observable<DouBanMovieBean> getTheatersMovie(@Query("city") String city);

    @GET("movie/coming_soon")
    Observable<DouBanMovieBean> getComingSoonMovie(@Query("start") int start, @Query("count") int count);

    @GET("movie/top250")
    Observable<DouBanMovieBean> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
