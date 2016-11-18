package com.memento.android.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferenceHelper {

    private static final String PRE_FILE_NAME = "pre_memento";
    private static final String PRE_THEME = "pre_theme";

    private Context mContext;
    private Gson mGson;
    private SharedPreferences mSharedPreferences;

    static PreferenceHelper instance;

    public static PreferenceHelper getInstance(Context context) {
        synchronized (PreferenceHelper.class){
            if(instance == null){
                instance = new PreferenceHelper(context);
            }
        }
        return instance;
    }

    private PreferenceHelper(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }
}  