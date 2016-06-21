package com.memento.android.data;


import com.memento.android.data.model.Address;
import com.memento.android.data.model.Article;
import com.memento.android.data.model.ArticleDetail;
import com.memento.android.data.model.LauncherImage;
import com.memento.android.data.model.Photo;

import java.io.File;
import java.util.List;

import rx.Observable;


public interface Repository {

    Observable<List<Photo>> getImageList();

    Observable<LauncherImage> getLauncherImage(String deviceInfo);

    Observable<List<Article>> getNewArticle(String date);

    Observable<List<Article>> getNewArticle();

    Observable<ArticleDetail> getArticleDetail(String id);

    Observable<File> downloadfile(String url);

    Observable<Address> getAddress(String ip);
}
