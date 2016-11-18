package com.memento.android.assistlibrary;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by caoruihuan on 16/10/13.
 */

public class AssistManager {

    public static void init(Application application){
        Logger.init();
        LeakCanary.install(application);
        Stetho.initializeWithDefaults(application);
    }
}
