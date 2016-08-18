package com.memento.android.data;


import android.content.Context;
import android.support.annotation.NonNull;

import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.store.DataStoreFactory;
import com.memento.android.injection.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class DataManager{

    private static final String TAG = DataManager.class.getSimpleName();

    private final Context mContext;
    private final DataStoreFactory mDataStoreFactory;

    @Inject
    public DataManager(@ApplicationContext Context context, @NonNull DataStoreFactory dataStoreFactory) {
        this.mContext = checkNotNull(context);
        this.mDataStoreFactory = checkNotNull(dataStoreFactory);
    }

    /**
     * 获取启动图
     * @return
     */
    public Observable<List<SplashImageEntity>> getImageList() {
        return mDataStoreFactory.create(KeyGenerator.generator(CacheKey.KEY_IMAGE_LIST)).getImageList();
    }

    /**
     * 知乎日报获取某天新闻
     * @param date
     * @return
     */
    public Observable<ZhihuArticleEntity> getNewArticle(String date) {
        return mDataStoreFactory.create(KeyGenerator.generator(CacheKey.KEY_ZHIHU_DATE, date)).getArticleList(date);
    }

    /**
     * 获取当天新闻
     * @return
     */
    public Observable<ZhihuArticleEntity> getNewArticle() {
        return mDataStoreFactory.create(KeyGenerator.generator(CacheKey.KEY_ZHIHU_NEWS)).getNewArticleList();
    }

    /**
     * 获取知乎文章详情
     * @param id
     * @return
     */
    public Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id) {
        return mDataStoreFactory.create(KeyGenerator.generator(CacheKey.KEY_ZHIHU_DETAIL, id)).getArticleDetail(id);
    }


    /**
     * 豆瓣查询正在上映电影
     * @param city
     * @return
     */
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        return mDataStoreFactory.create().getTheatersMovie(city);
    }

    /**
     * 豆瓣Top250电影
     * @param start
     * @param count
     * @return
     */
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return mDataStoreFactory.create().getTop250Movie(start, count);
    }

    /**
     * 豆瓣即将上映电影
     * @param start
     * @param count
     * @return
     */
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return mDataStoreFactory.create().getComingSoonMovie(start, count);
    }
}