package com.memento.android.data.repository.download;

import android.content.Context;
import android.net.Uri;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.memento.android.injection.ApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by android on 16-5-27.
 */
@Singleton
public class DownloadManager {

    private final Context mContext;

    @Inject
    public DownloadManager(@ApplicationContext Context mContext){
        this.mContext = mContext;
    }

    public Observable<String> downloadFile(final String url){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Uri uri = Uri.parse(url);
                FileDownloader.getImpl().create(url)
                        .setPath(mContext.getCacheDir()+ File.separator+getRandomFileName()+uri.getLastPathSegment())
                        .setListener(new FileDownloadListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                subscriber.onNext("soFarBytes:"+soFarBytes + "totalBytes:"+totalBytes);
                            }

                            @Override
                            protected void blockComplete(BaseDownloadTask task) {
                            }

                            @Override
                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {

                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {
                            }
                        }).start();
            }
        });
    }

    public String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }
}
