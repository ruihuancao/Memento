package com.crh.android.common.data.source.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by android on 16-11-22.
 */

public class RemoteHelper {

    /**
     * create http service
     * @param baseurl
     * @param cls
     * @param <T>
     * @return
     */
    public static  <T> T createService(String baseurl, OkHttpClient okHttpClient, Class<T> cls){
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseurl)
                .client(okHttpClient)
                .build().create(cls);
    }

    /**
     * 初始化OkHttpClient
     * @param isLog
     * @return
     */
    public static OkHttpClient initOkhttpClient(boolean isLog){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.addInterceptor(getLeanCloudInterceptor());
        builder.connectTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }

    /**
     * leancloud headers
     * @return
     */
    private static Interceptor getLeanCloudInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String host = original.url().host();
                if(host.equals("api.leancloud.cn")){
                    Request.Builder leancloudrequestBuilder = original.newBuilder()
                            .header("X-LC-Id", "qPcCKVCPUAgtOcSiVG1XVHHF-gzGzoHsz")
                            .header("Content-Type", "application/json")
                            .header("X-LC-Key", "rQbVq51Uo2pYHdtWyy4FHA7x");
                    return chain.proceed(leancloudrequestBuilder.build());
                }
                return chain.proceed(original);
            }
        };
    }
}
