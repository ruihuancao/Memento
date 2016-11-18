package com.example.datalibrary;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by caoruihuan on 16/10/13.
 */

public class DataManager {

    static DataManager instance;

    private OkHttpClient mOkHttpClient;

    public static DataManager getInstance(){
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager();
            }
            return instance;
        }
    }

    public DataManager(){

    }

    private void initClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志设置
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
        builder.addNetworkInterceptor(new StethoInterceptor());

        builder.addInterceptor(headInterceptor());
        //超时时间
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.build();
    }
}
