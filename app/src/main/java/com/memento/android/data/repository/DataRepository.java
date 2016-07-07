package com.memento.android.data.repository;


import android.content.Context;

import com.memento.android.data.Repository;
import com.memento.android.data.entity.DouBanMovieEntity;
import com.memento.android.data.entity.IpAddressEntity;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.data.repository.download.DownloadManager;
import com.memento.android.data.repository.file.FileManager;
import com.memento.android.data.repository.preference.SharePreferenceManager;
import com.memento.android.data.repository.remote.APIService;
import com.memento.android.injection.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataRepository implements Repository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private final Context mContext;
    private final APIService mAPIService;
    private final SharePreferenceManager mSharePreferenceManager;
    private final FileManager mFileManager;
    private final DownloadManager mDownloadManager;

    @Inject
    public DataRepository(@ApplicationContext Context context, APIService apiService,
                          SharePreferenceManager sharePreferenceManager, FileManager fileManager,
                          DownloadManager downloadManager) {
        this.mContext = context;
        this.mAPIService = apiService;
        this.mSharePreferenceManager = sharePreferenceManager;
        this.mFileManager = fileManager;
        this.mDownloadManager = downloadManager;
    }

    /**
     * 获取启动图
     * @return
     */
    @Override
    public Observable<List<SplashImageEntity>> getImageList() {
        return mAPIService.getSplash();
    }

    /**
     * 知乎日报获取某天新闻
     * @param date
     * @return
     */
    @Override
    public Observable<ZhihuArticleEntity> getNewArticle(String date) {
        return mAPIService.getArticleList(date);
    }

    /**
     * 获取当天新闻
     * @return
     */
    @Override
    public Observable<ZhihuArticleEntity> getNewArticle() {
        return mAPIService.getNewArticleList();
    }

    /**
     * 获取知乎文章详情
     * @param id
     * @return
     */
    @Override
    public Observable<ZhihuArticleDetailEmtity> getArticleDetail(String id) {
        return mAPIService.getArticleDetail(id);
    }

    /**
     * 用ip查看地址
     * @param ip
     * @return
     */
    @Override
    public Observable<IpAddressEntity> getAddress(String ip) {
        return mAPIService.getAddress(ip);
    }

    /**
     * 豆瓣查询正在上映电影
     * @param city
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getTheatersMovie(String city) {
        return mAPIService.getTheatersMovie(city);
    }

    /**
     * 豆瓣Top250电影
     * @param start
     * @param count
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getTop250Movie(int start, int count) {
        return mAPIService.getTop250Movie(start, count);
    }

    /**
     * 豆瓣即将上映电影
     * @param start
     * @param count
     * @return
     */
    @Override
    public Observable<DouBanMovieEntity> getComingSoonMovie(int start, int count) {
        return mAPIService.getComingSoonMovie(start, count);
    }
}