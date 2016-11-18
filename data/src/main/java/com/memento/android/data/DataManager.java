package com.memento.android.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.http.ApiService;
import com.memento.android.data.http.HttpHelper;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;


public class DataManager implements DataInterface{

    private static final String TAG = DataManager.class.getSimpleName();

    private static DataManager instance;

    private ApiService mApiService;
    private HttpHelper mHttpHelper;

    /**
     * 获取实例
     * @param cacheFile 缓存目录
     * @return
     */
    public static DataManager getInstance(File cacheFile) {
        return getInstance(cacheFile, false);
    }

    /**
     * 获取实例
     * @param cacheFile 缓存目录
     * @return
     */
    public static DataManager getInstance(File cacheFile, boolean isDebug) {
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager(cacheFile, isDebug);
            }
        }
        return instance;
    }

    /**
     * 构造函数
     * @param cacheFile 缓存目录
     */
    private DataManager(File cacheFile, boolean isDebug) {
        mHttpHelper = new HttpHelper(cacheFile, getGson(), isDebug);
        mApiService = mHttpHelper.getmApiService();
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

    /**
     * 获取启动图
     * @return
     */
    @Override
    public Observable<List<SplashImageEntity>> getImageList() {
        return mApiService.getSplash();
    }

    /**
     * 知乎日报获取某天新闻
     * @param date
     * @return
     */
    @Override
    public Observable<ZhihuArticleEntity> getNewArticle(String date) {
        return mApiService.getArticleList(date);
    }

    /**
     * 获取当天新闻
     * @return
     */
    @Override
    public Observable<ZhihuArticleEntity> getNewArticle() {
        return mApiService.getNewArticleList();
    }

    /**
     * 获取知乎文章详情
     * @param id
     * @return
     */
    @Override
    public Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id) {
        return mApiService.getArticleDetail(id);
    }


    /**
     * 豆瓣查询正在上映电影
     * @param city
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        return mApiService.getTheatersMovie(city);
    }

    /**
     * 豆瓣Top250电影
     * @param start
     * @param count
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return mApiService.getTop250Movie(start, count);
    }

    /**
     * 豆瓣即将上映电影
     * @param start
     * @param count
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return mApiService.getComingSoonMovie(start, count);
    }
}