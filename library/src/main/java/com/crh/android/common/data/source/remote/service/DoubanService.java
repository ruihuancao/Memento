package com.crh.android.common.data.source.remote.service;

import com.crh.android.common.data.source.entity.DouBanMovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by caoruihuan on 16/9/28.
 */

public interface DoubanService {
    String API_BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Observable<DouBanMovieEntity> getTheatersMovie(@Query("city") String city);

    @GET("movie/coming_soon")
    Observable<DouBanMovieEntity> getComingSoonMovie(@Query("start") int start, @Query("count") int count);

    @GET("movie/top250")
    Observable<DouBanMovieEntity> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
