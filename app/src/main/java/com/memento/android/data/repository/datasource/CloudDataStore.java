package com.memento.android.data.repository.datasource;

import com.memento.android.data.cache.Cache;
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
import com.memento.android.data.remote.APIService;
import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Action1;

public class CloudDataStore implements DataStore {

    private final APIService apiService;
    private Cache cache;
    private JsonSerializer jsonSerializer;

    public CloudDataStore(APIService apiService){
        this.apiService = apiService;
    }


    public CloudDataStore(APIService apiService, Cache cache, JsonSerializer jsonSerializer) {
        this.apiService = apiService;
        this.cache = cache;
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public Observable<List<SplashImage>> getSplashImage() {
        return apiService.getSplash().doOnNext(new Action1<List<SplashImage>>() {
            @Override
            public void call(List<SplashImage> splashImages) {
                if(cache != null){
                    Logger.d("add cache:"+ CacheName.SPLASHIMAGE);
                    cache.put(CacheName.SPLASHIMAGE, jsonSerializer.serialize(splashImages));
                }
            }
        });
    }

    @Override
    public Observable<ZhihuLauncherImage> getZhihuLauncherImage(String size) {
        return apiService.getStartImage(size).doOnNext(new Action1<ZhihuLauncherImage>() {
            @Override
            public void call(ZhihuLauncherImage zhihuLauncherImage) {
                if(cache != null){
                    Logger.d("add cache:"+ CacheName.ZHIHUIMAGE);
                    cache.put(CacheName.ZHIHUIMAGE, jsonSerializer.serialize(zhihuLauncherImage));
                }
            }
        });
    }

    @Override
    public Observable<ZhihuArticle> getArticleList(String date) {
        return apiService.getArticleList(date).doOnNext(new Action1<ZhihuArticle>() {
            @Override
            public void call(ZhihuArticle zhihuArticle) {
                if(cache != null){

                }
            }
        });
    }

    @Override
    public Observable<ZhihuArticle> getNewArticleList() {
        return apiService.getNewArticleList();
    }

    @Override
    public Observable<ZhihuArticleDetail> getArticleDetail(String id) {
        return apiService.getArticleDetail(id);
    }

    @Override
    public Observable<IpAddress> getAddress(String ip) {
        return apiService.getAddress(ip);
    }

    @Override
    public Observable<PhoneNumberLocaltion> getPhoneLocal(String phone) {
        return apiService.getPhoneLocal(phone);
    }

    @Override
    public Observable<Joke> getJoke(String time) {
        return apiService.getJoke(time);
    }

    @Override
    public Observable<HistoryToday> getHistoryToday(String month, String day) {
        return apiService.getHistoryToday(month, day);
    }

    @Override
    public Observable<WxArticleQuery> getWxArticleQuery(String page) {
        return apiService.getWxArticleQuery(page);
    }

    @Override
    public Observable<TvCategory> getTvCategory() {
        return apiService.getTvCategory();
    }

    @Override
    public Observable<TvList> getTvList(String pid) {
        return apiService.getTvList(pid);
    }

    @Override
    public Observable<TvProgram> getTvProgram(String code, String date) {
        return apiService.getTvProgram(code, date);
    }

    @Override
    public Observable<ResponseBody> downloadFileWithDynamicUrlSync(String fileUrl) {
        return apiService.downloadFileWithDynamicUrlSync(fileUrl);
    }
}
