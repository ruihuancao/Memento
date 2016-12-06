package com.crh.android.common.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
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


public class DataHelper {

    private static final String FILE_DOWNLOAD_DIRECTORY = "memento";
    private static final String FILE_SHARE_PREF         = "memento";

    public static String getDownLoadFileDirectory(){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), FILE_DOWNLOAD_DIRECTORY);
        if(!file.mkdirs()){
            Logger.d("public Directory not created");
        }
        return file.getAbsolutePath();
    }

    public static SharedPreferences getSharePreference(Context context){
        return context.getSharedPreferences(FILE_SHARE_PREF, Context.MODE_PRIVATE);
    }

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
