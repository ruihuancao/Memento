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

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

public class DiskDataStore implements DataStore {

    private Cache cache;
    private JsonSerializer jsonSerializer;

    public DiskDataStore(Cache cache, JsonSerializer jsonSerializer) {
        this.cache = cache;
        this.jsonSerializer = jsonSerializer;
    }

    @Override
    public Observable<List<SplashImage>> getSplashImage() {
        return this.cache.get(CacheName.SPLASHIMAGE).map(new Func1<String, List<SplashImage>>() {
            @Override
            public List<SplashImage> call(String s) {
                return jsonSerializer.deserializeListSplashImage(s);
            }
        });
    }

    @Override
    public Observable<ZhihuLauncherImage> getZhihuLauncherImage(String size) {
        return this.cache.get(CacheName.ZHIHUIMAGE).map(new Func1<String, ZhihuLauncherImage>() {
            @Override
            public ZhihuLauncherImage call(String s) {
                return jsonSerializer.deserialize(s);
            }
        });
    }

    @Override
    public Observable<ZhihuArticle> getArticleList(String date) {
        return null;
    }

    @Override
    public Observable<ZhihuArticle> getNewArticleList() {
        return null;
    }

    @Override
    public Observable<ZhihuArticleDetail> getArticleDetail(String id) {
        return null;
    }

    @Override
    public Observable<IpAddress> getAddress(String ip) {
        return null;
    }

    @Override
    public Observable<PhoneNumberLocaltion> getPhoneLocal(String phone) {
        return null;
    }

    @Override
    public Observable<Joke> getJoke(String time) {
        return null;
    }

    @Override
    public Observable<HistoryToday> getHistoryToday(String month, String day) {
        return null;
    }

    @Override
    public Observable<WxArticleQuery> getWxArticleQuery(String page) {
        return null;
    }

    @Override
    public Observable<TvCategory> getTvCategory() {
        return null;
    }

    @Override
    public Observable<TvList> getTvList(String pid) {
        return null;
    }

    @Override
    public Observable<TvProgram> getTvProgram(String code, String date) {
        return null;
    }

    @Override
    public Observable<ResponseBody> downloadFileWithDynamicUrlSync(String fileUrl) {
        return null;
    }
}
