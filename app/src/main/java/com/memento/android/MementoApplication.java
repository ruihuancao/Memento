package com.memento.android;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;


public class MementoApplication extends Application {

    private static final String TAG = MementoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        CrashReport.initCrashReport(getApplicationContext(), "900027540", false);

        Logger.init(TAG).logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

        Stetho.initializeWithDefaults(this);
    }


    public static MementoApplication get(Context context) {
        return (MementoApplication) context.getApplicationContext();
    }
}  