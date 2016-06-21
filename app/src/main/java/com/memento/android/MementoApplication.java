package com.memento.android;

import android.app.Application;
import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.facebook.stetho.Stetho;
import com.liulishuo.filedownloader.FileDownloader;
import com.memento.android.injection.component.ApplicationComponent;
import com.memento.android.injection.component.DaggerApplicationComponent;
import com.memento.android.injection.module.ApplicationModule;
import com.memento.android.leancloudlibrary.login.LearnCloud;
import com.memento.android.util.AppUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;


public class MementoApplication extends Application {

    private static final String TAG = MementoApplication.class.getSimpleName();

    ApplicationComponent mApplicationComponent;

    PatchManager mPatchManager;

    public PatchManager getPatchManager(){
        return mPatchManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        initDebugTool();
        //initAndFix();
        initBugly();
        initDownLoad();
        initLeanCloud();
    }

    private void initLeanCloud(){
        LearnCloud.init(this);
    }

    private void initDownLoad(){
        FileDownloader.init(getApplicationContext());
    }

    private void initBugly(){
        CrashReport.initCrashReport(getApplicationContext(), "900027540", false);
    }

    private void initAndFix(){
        try{
            mPatchManager = new PatchManager(this);
            mPatchManager.init(AppUtils.getAppVersionName(this, getPackageName()));//current version*//*
            mPatchManager.loadPatch();
        }catch (Exception e){
        }
    }

    public static MementoApplication get(Context context) {
        return (MementoApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    /**
     * 初始化Application组件
     */
    private void initComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    /**
     * 初始化工具
     */
    private void initDebugTool(){
        if(BuildConfig.DEBUG){
            Logger.init(TAG).logLevel(LogLevel.FULL);
            LeakCanary.install(this);
            Stetho.initializeWithDefaults(this);
        }else{
            Logger.init(TAG).logLevel(LogLevel.NONE);
        }
    }
}  