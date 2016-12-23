package com.memento.android.data.config;

import android.content.SharedPreferences;


public class ConfigManager {

    private SharedPreferences mPref;

    public ConfigManager(SharedPreferences mPref) {
        this.mPref = mPref;
    }

    public void setStringValue(String key, String value){
        if (mPref == null) return;
        mPref.edit().putString(key, value).apply();
    }

    public String getStringValue(String key, String defaultValue){
        if (mPref == null) return defaultValue;
        return mPref.getString(key, defaultValue);
    }

    public String getStringValue(String key){
        if (mPref == null) return "";
        return mPref.getString(key, "");
    }

    public void setBooleanValue(String key, boolean value){
        if (mPref == null) return;
        mPref.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key, boolean defaultValue){
        if (mPref == null) return defaultValue;
        return mPref.getBoolean(key, defaultValue);
    }


    public boolean getBooleanValue(String key){
        if (mPref == null) return false;
        return mPref.getBoolean(key, false);
    }


    public void setLongValue(String key, long value){
        if (mPref == null) return;
        mPref.edit().putLong(key, value).apply();
    }

    public long getLongValue(String key){
        if (mPref == null) return 0;
        return mPref.getLong(key, 0);
    }
    public long getLongValue(String key, long defaultValue){
        if (mPref == null) return defaultValue;
        return mPref.getLong(key, defaultValue);
    }
}
