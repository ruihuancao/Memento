package com.memento.android.data.repository.datasource;

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

public interface DataStore {

    Observable<List<SplashImage>> getSplashImage();

    Observable<ZhihuLauncherImage> getZhihuLauncherImage(String size);

    Observable<ZhihuArticle> getArticleList(String date);

    Observable<ZhihuArticle> getNewArticleList();

    Observable<ZhihuArticleDetail> getArticleDetail(String id);

    Observable<IpAddress> getAddress(String ip);

    Observable<PhoneNumberLocaltion> getPhoneLocal(String phone);

    Observable<Joke> getJoke(String time);

    Observable<HistoryToday> getHistoryToday(String month, String day);

    Observable<WxArticleQuery> getWxArticleQuery(String page);

    Observable<TvCategory> getTvCategory();

    Observable<TvList> getTvList(String pid);

    Observable<TvProgram> getTvProgram(String code, String date);

    Observable<ResponseBody> downloadFileWithDynamicUrlSync(String fileUrl);

}
