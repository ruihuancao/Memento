package com.memento.android.data.store.http;


import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.memento.android.BuildConfig;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.injection.ApplicationContext;
import com.memento.android.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class HttpHelper {

    private final Context mContext;

    private OkHttpClient mOkHttpClient;
    private SystemApiService mSystemApiService;
    private ThirdApiService mThirdApiService;
    private Gson mGson;

    @Inject
    public HttpHelper(@ApplicationContext Context context) {
        mContext = checkNotNull(context);
        mGson = getGson();
        mOkHttpClient = getClient(context, BuildConfig.DEBUG);
        mSystemApiService = getRetrofit(SystemApiService.API_BASE_URL, mOkHttpClient, mGson)
                .create(SystemApiService.class);
        mThirdApiService = getRetrofit(ThirdApiService.API_BASE_URL, mOkHttpClient, mGson)
                .create(ThirdApiService.class);
    }

    public SystemApiService getmSystemApiService() {
        return mSystemApiService;
    }

    public ThirdApiService getmThirdApiService() {
        return mThirdApiService;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 返回okhttpclient
     * @param context
     * @param isCache
     * @return
     */
    public OkHttpClient getClient(final Context context, boolean isCache){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志设置
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);

        // Stetho 浏览器调试网络请求
        builder.addNetworkInterceptor(new StethoInterceptor());

        // 配置缓存
        if(isCache){
            // 请求拦截器
            Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if(!NetUtils.isNetworkAvailable(context)){
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response originalResponse = chain.proceed(request);
                    if(NetUtils.isNetworkAvailable(context)){
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    }else{
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                                .removeHeader("Pragma")
                                .build();
                    }
                }
            };

            //设置缓存文件
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            builder.addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .cache(cache);
        }else{

        }

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

    /**
     * 自定义类型转换
     * @return
     */
    public Gson getGson(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZhihuArticleEntity.class, new JsonDeserializer<ZhihuArticleEntity>() {
                    @Override
                    public ZhihuArticleEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                        return null;
                    }
                }).create();

        return new Gson();
    }
}
