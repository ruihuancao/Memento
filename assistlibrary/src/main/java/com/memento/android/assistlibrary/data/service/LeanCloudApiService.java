package com.memento.android.assistlibrary.data.service;

import com.memento.android.assistlibrary.data.entity.LeanCloudUser;

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
    Observable<LeanCloudUser> register(@Body LeanCloudUser user);

    @GET("users/{objectId}")
    Observable<LeanCloudUser> getUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId);

    @PUT("users/{objectId}")
    Observable<LeanCloudUser> updateUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId, @Body LeanCloudUser user);

    @POST("login")
    Observable<LeanCloudUser> login(@Query("username")String name, @Query("password")String password);

    @POST("login")
    Observable<LeanCloudUser> login(@Body LeanCloudUser user);

    @POST("users/me")
    Observable<LeanCloudUser> getCurrentUser(@Header("X-LC-Session") String session);

    @POST("requestEmailVerify")
    Observable<LeanCloudUser> requestEmailVerify(String email);

    @POST("requestPasswordReset")
    Observable<LeanCloudUser> requestPasswordReset(String email);

}
