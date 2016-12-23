package com.memento.android.data.source.remote.service;

import com.memento.android.bean.LeanCloudUserBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface LeanCloudApiService {
    String API_BASE_URL = "https://api.leancloud.cn/1.1/";

    //注册
    @POST("users")
    Observable<LeanCloudUserBean> register(@Body LeanCloudUserBean user);

    @GET("users/{objectId}")
    Observable<LeanCloudUserBean> getUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId);

    @PUT("users/{objectId}")
    Observable<LeanCloudUserBean> updateUser(@Header("X-LC-Session") String session, @Path("objectId")String objectId, @Body LeanCloudUserBean user);

    @POST("login")
    Observable<LeanCloudUserBean> login(@Query("username")String name, @Query("password")String password);

    @POST("login")
    Observable<LeanCloudUserBean> login(@Body LeanCloudUserBean user);

    @POST("users/me")
    Observable<LeanCloudUserBean> getCurrentUser(@Header("X-LC-Session") String session);

    @POST("requestEmailVerify")
    Observable<LeanCloudUserBean> requestEmailVerify(String email);

    @POST("requestPasswordReset")
    Observable<LeanCloudUserBean> requestPasswordReset(String email);

}
