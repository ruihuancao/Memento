package com.memento.android.data.http;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpHelper {

    private OkHttpClient mOkHttpClient;
    private ApiService mApiService;
    private boolean isDebug = false;


    public HttpHelper(File cacheFile, Gson gson, boolean isDebug) {
        this.isDebug = isDebug;
        mOkHttpClient = getClient(cacheFile);
        mApiService = getRetrofit(ApiService.API_BASE_URL, mOkHttpClient, gson)
                .create(ApiService.class);
    }

    public ApiService getmApiService() {
        return mApiService;
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 返回okhttpclient
     * @return
     */
    public OkHttpClient getClient(File cacheFile){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志设置
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);

        if(isDebug){
            // Stetho 浏览器调试网络请求
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        
        //设置缓存文件
        File httpCacheDirectory = new File(cacheFile, "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder.cache(cache);

        //超时时间
        builder.connectTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }

    /**
     * 返回Retrofit
     * @param baseUrl
     * @param okHttpClient
     * @return
     */
    public Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }
}
