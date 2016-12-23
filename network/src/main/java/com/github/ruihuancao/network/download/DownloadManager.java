package com.github.ruihuancao.network.download;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.github.ruihuancao.network.util.FileUtils;
import com.github.ruihuancao.network.util.MD5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class DownloadManager {

    private static final String TAG = DownloadManager.class.getSimpleName();
    private static final String PATH = "download";

    private String downloadPath;
    private OkHttpClient mOkHttpClient;

    public DownloadManager(Context context, OkHttpClient okHttpClient){
        this.mOkHttpClient = okHttpClient;
        this.downloadPath = FileUtils.getDiskCacheDir(context, PATH);
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
            fileName = MD5.md5Hex(url) + Uri.parse(url).getLastPathSegment();
        }else{
            fileName = MD5.md5Hex(url) + fileName;
        }
        File file = new File(downloadPath, fileName);
        return new DownLoad(url, file.getPath());
    }

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
                        subscriber.onError(new Exception("download fail"));
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public class DownLoad{

        private String url;
        private long total;
        private String filePath;
        private int progress;
        private boolean done;

        public DownLoad(String url, String filePath) {
            this.url = url;
            this.filePath = filePath;
            this.total = 0;
            this.progress = 0;
            this.done = false;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
