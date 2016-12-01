package com.memento.android.ui.test;


import android.content.Context;

public class DataManager {

    private volatile static DataManager instance;

    private Context mContext;

    private DataManager(Context context){
        this.mContext = context;
    }

    public static DataManager getInstance(Context context){
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager(context);
            }
        }
        return instance;
    }
}
