package com.memento.android.assistlibrary.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.memento.android.assistlibrary.data.service.DoubanService;
import com.memento.android.assistlibrary.data.service.DxyerApiService;
import com.memento.android.assistlibrary.data.service.LeanCloudApiService;
import com.memento.android.assistlibrary.data.service.TulingApiService;
import com.memento.android.assistlibrary.data.service.ZhihuApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by caoruihuan on 16/9/28.
 */

public class DataManager {

    static DataManager instance;
    private Context mContext;

    private OkHttpClient mOkHttpClient;
    private ZhihuApiService mZhihuApiService;
    private DxyerApiService mDxyerApiService;
    private TulingApiService mTulingApiService;
    private LeanCloudApiService mLeanCloudApiService;
    private DoubanService mDoubanService;

    public DataManager(Context context){
        this.mContext = context;
        mOkHttpClient = buildOkhttpClient();
        mZhihuApiService = createService(ZhihuApiService.API_BASE_URL, ZhihuApiService.class);
        mDoubanService = createService(DoubanService.API_BASE_URL, DoubanService.class);
        mDxyerApiService = createService(DxyerApiService.API_BASE_URL, DxyerApiService.class);
        mTulingApiService = createService(TulingApiService.API_BASE_URL, TulingApiService.class);
        mLeanCloudApiService = createService(LeanCloudApiService.API_BASE_URL, LeanCloudApiService.class);
    }

    public static DataManager getInstance(Context context){
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager(context);
            }
            return instance;
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public ZhihuApiService getZhihuApiService() {
        return mZhihuApiService;
    }

    public DxyerApiService getDxyerApiService() {
        return mDxyerApiService;
    }

    public TulingApiService getTulingApiService() {
        return mTulingApiService;
    }

    public LeanCloudApiService getLeanCloudApiService() {
        return mLeanCloudApiService;
    }

    public DoubanService getDoubanService() {
        return mDoubanService;
    }


    private OkHttpClient buildOkhttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkAvailable(mContext)){
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (isNetworkAvailable(mContext)){
                    int maxAge = 0;
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                }else{
                    int maxStale = 60* 60* 24 * 7;
                    response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        });

        builder.addInterceptor(getLeanCloudInterceptor());

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(mContext.getCacheDir(), "httpcache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        builder.cache(cache);

        //超时时间
        builder.connectTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }

    private Interceptor getLeanCloudInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String host = original.url().host();
                if(host.equals("api.leancloud.cn")){
                    Request.Builder leancloudrequestBuilder = original.newBuilder()
                            .header("X-LC-Id", "qPcCKVCPUAgtOcSiVG1XVHHF-gzGzoHsz")
                            .header("Content-Type", "application/json")
                            .header("X-LC-Key", "rQbVq51Uo2pYHdtWyy4FHA7x");
                    return chain.proceed(leancloudrequestBuilder.build());
                }
                return chain.proceed(original);
            }
        };
    }

    private <T> T createService(String baseurl, Class<T> cls){
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseurl)
                .client(getOkHttpClient())
                .build().create(cls);
    }

    public  <T> Subscription doSubscribe(Subscriber<? super T> subscriber, Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Observable<String> downloadFile(final String url, final File file){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        inputStream = response.body().byteStream();
                        long length = response.body().contentLength();
                        outputStream = new FileOutputStream(file);
                        byte data[] = new byte[1024];
                        subscriber.onNext("0%");
                        long total = 0;
                        int count;
                        while ((count = inputStream.read(data)) != -1){
                            total += count;
                            subscriber.onNext(String.valueOf(total*100/length) + "%");
                            outputStream.write(data,0,count);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                    }
                }catch (IOException e){
                    subscriber.onError(e);
                }finally {
                    if (inputStream != null){
                        try{
                            inputStream.close();
                        }catch (IOException e){}
                    }
                    if (outputStream != null){
                        try {
                            outputStream.close();
                        }catch (IOException e){}
                    }
                }
                subscriber.onCompleted();
            }
        });
    }



    private Gson getGson(){
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(ZhihuArticleEntity.class, new JsonDeserializer<ZhihuArticleEntity>() {
//                    @Override
//                    public ZhihuArticleEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                        return null;
//                    }
//                }).create();
        return new Gson();
    }

    private boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                return info.isAvailable();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
