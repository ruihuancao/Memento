package com.memento.android.data.repository;


import android.content.Context;
import android.util.Log;

import com.memento.android.data.Repository;
import com.memento.android.data.entity.IpAddress;
import com.memento.android.data.entity.SplashImage;
import com.memento.android.data.entity.ZhihuArticle;
import com.memento.android.data.entity.ZhihuArticleDetail;
import com.memento.android.data.entity.ZhihuLauncherImage;
import com.memento.android.data.mapper.DataMapper;
import com.memento.android.data.model.Address;
import com.memento.android.data.model.Article;
import com.memento.android.data.model.ArticleDetail;
import com.memento.android.data.model.LauncherImage;
import com.memento.android.data.model.Photo;
import com.memento.android.data.repository.datasource.CacheName;
import com.memento.android.data.repository.datasource.DataStoreFactory;
import com.memento.android.injection.ApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

@Singleton
public class DataRepository implements Repository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private final DataMapper mDataMapper;
    private final DataStoreFactory dataStoreFactory;
    private final Context mContext;

    @Inject
    public DataRepository(@ApplicationContext Context context, DataStoreFactory dataStoreFactory, DataMapper dataMapper) {
        this.mContext = context;
        this.mDataMapper = dataMapper;
        this.dataStoreFactory = dataStoreFactory;
    }


    @Override
    public Observable<List<Photo>> getImageList() {
        return dataStoreFactory.create(CacheName.SPLASHIMAGE)
                .getSplashImage()
                .map(new Func1<List<SplashImage>, List<Photo>>() {
                    @Override
                    public List<Photo> call(List<SplashImage> list) {
                        return mDataMapper.transform(list);
                    }
                });
    }

    @Override
    public Observable<LauncherImage> getLauncherImage(String deviceInfo) {
        return dataStoreFactory.create(CacheName.ZHIHUIMAGE)
                .getZhihuLauncherImage(deviceInfo)
                .map(new Func1<ZhihuLauncherImage, LauncherImage>() {
                    @Override
                    public LauncherImage call(ZhihuLauncherImage zhihuLauncherImage) {
                        return mDataMapper.transform(zhihuLauncherImage);
                    }
                });
    }

    @Override
    public Observable<List<Article>> getNewArticle(String date) {
        return dataStoreFactory.create().getArticleList(date)
                .map(new Func1<ZhihuArticle, List<Article>>() {
                    @Override
                    public List<Article> call(ZhihuArticle zhihuArticle) {
                        return mDataMapper.transform(zhihuArticle);
                    }
                });
    }

    @Override
    public Observable<List<Article>> getNewArticle() {
        return dataStoreFactory.create().getNewArticleList()
                .map(new Func1<ZhihuArticle, List<Article>>() {
                    @Override
                    public List<Article> call(ZhihuArticle zhihuArticle) {
                        return mDataMapper.transform(zhihuArticle);
                    }
                });
    }

    @Override
    public Observable<ArticleDetail> getArticleDetail(String id) {
        return dataStoreFactory.create().getArticleDetail(id)
                .map(new Func1<ZhihuArticleDetail, ArticleDetail>() {
                    @Override
                    public ArticleDetail call(ZhihuArticleDetail zhihuArticleDetail) {
                        return mDataMapper.transform(zhihuArticleDetail);
                    }
                });
    }

    @Override
    public Observable<File> downloadfile(String url) {
        return dataStoreFactory.create().downloadFileWithDynamicUrlSync(url)
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        try {
                            File futureStudioIconFile = new File(mContext.getCacheDir()+File.separator+"image.jpg");
                            InputStream inputStream = null;
                            OutputStream outputStream = null;
                            try {
                                byte[] fileReader = new byte[4096];
                                long fileSize = responseBody.contentLength();
                                long fileSizeDownloaded = 0;
                                inputStream = responseBody.byteStream();
                                outputStream = new FileOutputStream(futureStudioIconFile);
                                while (true) {
                                    int read = inputStream.read(fileReader);
                                    if (read == -1) {
                                        break;
                                    }
                                    outputStream.write(fileReader, 0, read);
                                    fileSizeDownloaded += read;
                                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                                }
                                outputStream.flush();
                                return futureStudioIconFile;
                            } catch (IOException e) {
                                return null;
                            } finally {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (outputStream != null) {
                                    outputStream.close();
                                }
                            }
                        } catch (IOException e) {
                            return null;
                        }
                    }
                });
    }


    @Override
    public Observable<Address> getAddress(String ip) {
        return dataStoreFactory.create().getAddress(ip)
                .map(new Func1<IpAddress, Address>() {
                    @Override
                    public Address call(IpAddress ipAddress) {
                        return mDataMapper.transform(ipAddress);
                    }
                });
    }
}