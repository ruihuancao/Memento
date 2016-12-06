package com.crh.android.common.data.download;

import android.net.Uri;
import android.text.TextUtils;

import com.crh.android.common.data.DataHelper;
import com.crh.android.common.data.exception.ApiException;
import com.crh.android.common.util.MD5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * 文件下载
 */
public class DownloadManager {
    private OkHttpClient mOkHttpClient;
    public DownloadManager(OkHttpClient okHttpClient){
        this.mOkHttpClient = okHttpClient;
    }

    /**
     * 下载文件
     * @param url
     * @return
     */
    public Observable<DownLoad> downLoadFile(String url){
        return downLoadFile(url, null);
    }

    /**
     * 下载文件
     * @param url
     * @param fileName
     * @return
     */
    public Observable<DownLoad> downLoadFile(String url, String fileName){
        return downLoad(buildDownload(url, fileName));
    }

    /**
     * 准备下载参数
     * @param url
     * @param fileName
     * @return
     */
    private DownLoad buildDownload(String url, String fileName){
        if(TextUtils.isEmpty(fileName)){
            fileName = MD5Util.md5Hex(url) + Uri.parse(url).getLastPathSegment();
        }else{
            fileName = MD5Util.md5Hex(url) + fileName;
        }
        File file = new File(DataHelper.getDownLoadFileDirectory(), fileName);
        return new DownLoad(url, file.getAbsolutePath());
    }

    /**
     * 下载
     * @param downLoad
     * @return
     */
    private Observable<DownLoad> downLoad(final DownLoad downLoad) {
        return Observable.create(new Observable.OnSubscribe<DownLoad>() {
            @Override
            public void call(Subscriber<? super DownLoad> subscriber) {
                try{
                    Request request = new Request.Builder()
                            .url(downLoad.getUrl())
                            .build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if(response.isSuccessful()){
                        File file = new File(downLoad.getFilePath());
                        byte[] buf = new byte[2048];
                        int len = 0;
                        long current = 0;
                        long total = response.body().contentLength();
                        int progress = 0;
                        downLoad.setTotal(total);
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            current += len;
                            progress = (int)((current * 100) / total);
                            if(progress != downLoad.getProgress()){
                                downLoad.setProgress(progress);
                                subscriber.onNext(downLoad);
                            }
                        }
                        fos.flush();
                        is.close();
                        fos.close();
                        downLoad.setDone(true);
                        subscriber.onNext(downLoad);
                        subscriber.onCompleted();
                    }else{
                        subscriber.onError(new ApiException());
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
