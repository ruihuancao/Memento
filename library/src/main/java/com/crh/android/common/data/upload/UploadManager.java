package com.crh.android.common.data.upload;

import com.crh.android.common.data.exception.ApiException;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class UploadManager {

    private OkHttpClient mOkHttpClient;

    public UploadManager(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
    }

    public Observable<String> upLoadFile(final String url, final HashMap<String, Object> paramsMap) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    //设置类型
                    builder.setType(MultipartBody.FORM);
                    //追加参数
                    for (String key : paramsMap.keySet()) {
                        Object object = paramsMap.get(key);
                        if (!(object instanceof File)) {
                            builder.addFormDataPart(key, object.toString());
                        } else {
                            File file = (File) object;
                            builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                        }
                    }
                    RequestBody body = builder.build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    final Call call = mOkHttpClient.newBuilder()
                            .writeTimeout(50, TimeUnit.SECONDS)
                            .build().newCall(request);
                    Response response = call.execute();
                    if(response.isSuccessful()){
                        subscriber.onNext(response.body().string());
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
