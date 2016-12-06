package com.crh.android.common.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crh.android.common.data.config.ConfigManager;
import com.crh.android.common.data.download.DownloadManager;
import com.crh.android.common.data.source.local.LocalDataSource;
import com.crh.android.common.data.source.local.cache.Cache;
import com.crh.android.common.data.source.local.dao.DaoSession;
import com.crh.android.common.data.source.local.dao.GreenDao;
import com.crh.android.common.data.source.real.RealDataSource;
import com.crh.android.common.data.source.remote.RemoteDataSource;
import com.crh.android.common.data.upload.UploadManager;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class DataManager{

    private volatile static DataManager instance;
    @NonNull
    private final DataSource mRemoteDataSource;
    @NonNull
    private final DataSource mLocalDataSource;
    @NonNull
    private final DataSource mRealDataSource;
    @NonNull
    private Cache mCache;
    @NonNull
    private DaoSession mDaoSession;
    @NonNull
    private Gson mGson;
    @NonNull
    private OkHttpClient mOkHttpClient;
    @NonNull
    private DownloadManager mDownloadManager;
    @NonNull
    private UploadManager mUploadManager;
    @NonNull
    private ConfigManager mConfigManager;


    private DataManager(@NonNull Context context) {
        this.mOkHttpClient = DataHelper.initOkhttpClient(false);
        this.mDaoSession = GreenDao.getInstance(context).getDaoSession();
        this.mGson = new Gson();
        this.mRemoteDataSource = new RemoteDataSource(mGson, mOkHttpClient);
        this.mCache = new Cache(context, mDaoSession);
        this.mLocalDataSource = new LocalDataSource(mCache, mGson);
        this.mRealDataSource = new RealDataSource(mLocalDataSource, mRemoteDataSource, mCache, mGson);
        this.mDownloadManager = new DownloadManager(mOkHttpClient);
        this.mUploadManager = new UploadManager(mOkHttpClient);
        this.mConfigManager = new ConfigManager(DataHelper.getSharePreference(context));
    }

    public static DataManager getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (DataManager.class){
                if(instance == null){
                    instance = new DataManager(context);
                }
            }
        }
        return instance;
    }

    public DataSource getDataSource(){
        return this.mRealDataSource;
    }

    @NonNull
    public DownloadManager getDownloadManager() {
        return mDownloadManager;
    }

    @NonNull
    public UploadManager getUploadManager() {
        return mUploadManager;
    }
}
