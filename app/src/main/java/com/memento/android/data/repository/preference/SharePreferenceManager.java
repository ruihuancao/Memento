package com.memento.android.data.repository.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.memento.android.injection.ApplicationContext;
import com.memento.android.ui.theme.MaterialTheme;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-06-24
 * 时间: 14:57
 * 描述：
 * 修改历史：
 */
@Singleton
public class SharePreferenceManager {

    private static final String PRE_FILE_NAME = "pre_memento";
    private static final String PRE_THEME = "pre_theme";

    private Context mContext;
    private Gson mGson;
    private SharedPreferences mSharedPreferences;

    @Inject
    public SharePreferenceManager(@ApplicationContext Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PRE_FILE_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }


    public void addMaterilaTheme(MaterialTheme materialTheme){
        if(materialTheme == null){
            return;
        }
        String data = mGson.toJson(materialTheme);
        mSharedPreferences.edit().putString(PRE_THEME, data).apply();
    }

    public MaterialTheme getCurrentTheme(){
        String currentStr = mSharedPreferences.getString(PRE_THEME, "");
        MaterialTheme currentTheme = null;
        if(TextUtils.isEmpty(currentStr)){
            currentTheme = MaterialTheme.getThemeList().get(0);
            addMaterilaTheme(currentTheme);
        }else{
            currentTheme = new Gson().fromJson(currentStr, MaterialTheme.class);
        }
        return currentTheme;
    }

}  