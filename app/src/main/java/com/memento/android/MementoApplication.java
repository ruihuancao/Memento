package com.memento.android;

import android.app.Application;
import android.content.Context;

import com.memento.android.assistlibrary.AssistManager;
import com.tencent.bugly.crashreport.CrashReport;


public class MementoApplication extends Application {

    private static final String TAG = MementoApplication.class.getSimpleName();

    static MementoApplication mementoApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mementoApplication = this;
        CrashReport.initCrashReport(getApplicationContext(), "900027540", false);
        AssistManager.init(this);
    }

    public static MementoApplication getMementoApplication() {
        return mementoApplication;
    }

    public static MementoApplication get(Context context) {
        return (MementoApplication) context.getApplicationContext();
    }
}  