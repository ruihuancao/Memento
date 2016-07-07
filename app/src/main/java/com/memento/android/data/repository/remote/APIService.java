package com.memento.android.data.repository.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.HistoryTodayEntity;
import com.memento.android.data.entity.IpAddressEntity;
import com.memento.android.data.entity.JokeEntity;
import com.memento.android.data.entity.PhoneNumberLocaltionEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.TvCategoryEntity;
import com.memento.android.data.entity.TvListEntity;
import com.memento.android.data.entity.TvProgramEntity;
import com.memento.android.data.entity.WxArticleQueryEntity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


public interface APIService {

    String API_BASE_URL = "http://news-at.main.com";

    @GET("https://unsplash.it/list")
    Observable<List<SplashImageEntity>> getSplash();

    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    Observable<ZhihuArticleEntity> getArticleList(@Path("date") String date);

    @GET("http://news-at.zhihu.com/api/4/news/latest")
    Observable<ZhihuArticleEntity> getNewArticleList();

    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<ZhihuArticleDetailEmtity> getArticleDetail(@Path("id") String id);

    //根据ip获取地址
    @GET("http://apis.juhe.cn/ip/ip2addr?key=7b60605a7a9cc1b52576363f282c38fa&dtype=json")
    Observable<IpAddressEntity> getAddress(@Query("ip") String ip);

    //手机号码归属地查询
    @GET("http://apis.juhe.cn/mobile/get?dtype=json&key=a19615137e9457a7c65ad72decbb76bf")
    Observable<PhoneNumberLocaltionEntity> getPhoneLocal(@Query("phone") String phone);

    //http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=20&time=2418816972&key=41b1a3e792c5dd9dc0e7b69a3b662d82
    @GET("http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=20&key=41b1a3e792c5dd9dc0e7b69a3b662d82")
    Observable<JokeEntity> getJoke(@Query("time") String time);

    @GET("http://api.juheapi.com/japi/toh?v=1.0&key=27c3ea8b9f94a59d6851efbefef861a1")
    Observable<HistoryTodayEntity> getHistoryToday(@Query("month") String month, @Query("day") String day);

    @GET("http://v.juhe.cn/weixin/query?ps=20&dtype=json&key=19ffa78faf954238de02fc9ec056d04e")
    Observable<WxArticleQueryEntity> getWxArticleQuery(@Query("pno") String page);

    @GET("http://japi.juhe.cn/tv/getCategory?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvCategoryEntity> getTvCategory();

    @GET("http://japi.juhe.cn/tv/getChannel?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvListEntity> getTvList(@Query("pId") String pid);

    //code=cctv1&date=2016-05-06
    @GET("http://japi.juhe.cn/tv/getProgram?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvProgramEntity> getTvProgram(@Query("code") String code, @Query("date") String date);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("https://api.douban.com/v2/movie/in_theaters")
    Observable<DouBanMovieEntity> getTheatersMovie(@Query("city")String city);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("https://api.douban.com/v2/movie/coming_soon")
    Observable<DouBanMovieEntity> getComingSoonMovie(@Query("start")int start, @Query("count")int count);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("https://api.douban.com/v2/movie/top250")
    Observable<DouBanMovieEntity> getTop250Movie(@Query("start")int start, @Query("count")int count);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    class Factory {

        public static APIService createService(final Context context) {
            //日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


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

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .cache(cache)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();

            return new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(API_BASE_URL)
                    .client(client)
                    .build()
                    .create(APIService.class);
        }
    }

}
