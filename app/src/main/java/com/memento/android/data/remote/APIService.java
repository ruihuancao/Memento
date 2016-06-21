package com.memento.android.data.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.memento.android.data.entity.HistoryToday;
import com.memento.android.data.entity.IpAddress;
import com.memento.android.data.entity.Joke;
import com.memento.android.data.entity.PhoneNumberLocaltion;
import com.memento.android.data.entity.SplashImage;
import com.memento.android.data.entity.TvCategory;
import com.memento.android.data.entity.TvList;
import com.memento.android.data.entity.TvProgram;
import com.memento.android.data.entity.WxArticleQuery;
import com.memento.android.data.entity.ZhihuArticle;
import com.memento.android.data.entity.ZhihuArticleDetail;
import com.memento.android.data.entity.ZhihuLauncherImage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


public interface APIService {

    String API_BASE_URL = "http://news-at.zhihu.com";

    @GET("https://unsplash.it/list")
    Observable<List<SplashImage>> getSplash();

    @GET("http://news-at.zhihu.com/api/4/start-image/{size}")
    Observable<ZhihuLauncherImage> getStartImage(@Path("size") String size);

    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    Observable<ZhihuArticle> getArticleList(@Path("date") String date);

    @GET("http://news-at.zhihu.com/api/4/stories/latest")
    Observable<ZhihuArticle> getNewArticleList();

    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<ZhihuArticleDetail> getArticleDetail(@Path("id") String id);

    //根据ip获取地址
    @GET("http://apis.juhe.cn/ip/ip2addr?key=7b60605a7a9cc1b52576363f282c38fa&dtype=json")
    Observable<IpAddress> getAddress(@Query("ip") String ip);

    //手机号码归属地查询
    @GET("http://apis.juhe.cn/mobile/get?dtype=json&key=a19615137e9457a7c65ad72decbb76bf")
    Observable<PhoneNumberLocaltion> getPhoneLocal(@Query("phone") String phone);

    //http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=20&time=2418816972&key=41b1a3e792c5dd9dc0e7b69a3b662d82
    @GET("http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=20&key=41b1a3e792c5dd9dc0e7b69a3b662d82")
    Observable<Joke> getJoke(@Query("time") String time);

    @GET("http://api.juheapi.com/japi/toh?v=1.0&key=27c3ea8b9f94a59d6851efbefef861a1")
    Observable<HistoryToday> getHistoryToday(@Query("month") String month, @Query("day") String day);

    @GET("http://v.juhe.cn/weixin/query?ps=20&dtype=json&key=19ffa78faf954238de02fc9ec056d04e")
    Observable<WxArticleQuery> getWxArticleQuery(@Query("pno") String page);

    @GET("http://japi.juhe.cn/tv/getCategory?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvCategory> getTvCategory();

    @GET("http://japi.juhe.cn/tv/getChannel?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvList> getTvList(@Query("pId") String pid);

    //code=cctv1&date=2016-05-06
    @GET("http://japi.juhe.cn/tv/getProgram?key=04d9cafa50eb8255669dc6dd6eacf1b8")
    Observable<TvProgram> getTvProgram(@Query("code") String code, @Query("date") String date);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    class Factory {

        public static APIService createService(final Context context) {
            //日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

          /*  Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    if (DataUtil.isNetWorkAvailable(context)) {
                        int maxAge = 60; // 在线缓存在1分钟内可读取
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    } else {
                        int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                }
            };*/

            //设置缓存文件
/*            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);*/

            OkHttpClient client = new OkHttpClient.Builder()
                    //.cache(cache)
                    .addInterceptor(logging)
 /*                   .addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)*/
                    .addNetworkInterceptor(new StethoInterceptor())
                    .retryOnConnectionFailure(true)
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
