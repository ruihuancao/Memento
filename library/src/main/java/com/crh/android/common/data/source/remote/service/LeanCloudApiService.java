package com.crh.android.common.data.source.remote.service;

import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by caoruihuan on 16/9/28.
 */

public interface LeanCloudApiService {
    String API_BASE_URL = "https://api.leancloud.cn/1.1/";

    //注册
    @POST("users")
    Observable<LeanCloudUserEntiry> register(@Body LeanCloudUserEntiry user);

    @GET("users/{objectId}")
    Observable<LeanCloudUserEntiry> getUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId);

    @PUT("users/{objectId}")
    Observable<LeanCloudUserEntiry> updateUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId, @Body LeanCloudUserEntiry user);

    @POST("login")
    Observable<LeanCloudUserEntiry> login(@Query("username")String name, @Query("password")String password);

    @POST("login")
    Observable<LeanCloudUserEntiry> login(@Body LeanCloudUserEntiry user);

    @POST("users/me")
    Observable<LeanCloudUserEntiry> getCurrentUser(@Header("X-LC-Session") String session);

    @POST("requestEmailVerify")
    Observable<LeanCloudUserEntiry> requestEmailVerify(String email);

    @POST("requestPasswordReset")
    Observable<LeanCloudUserEntiry> requestPasswordReset(String email);

}
