package com.memento.android.data.http;

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
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 曹瑞环 on 2016/8/10.
 */
public interface ApiService {

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
    Observable<DouBanMovieEntity> getTheatersMovie(@Query("city") String city);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("https://api.douban.com/v2/movie/coming_soon")
    Observable<DouBanMovieEntity> getComingSoonMovie(@Query("start") int start, @Query("count") int count);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("https://api.douban.com/v2/movie/top250")
    Observable<DouBanMovieEntity> getTop250Movie(@Query("start") int start, @Query("count") int count);
}
