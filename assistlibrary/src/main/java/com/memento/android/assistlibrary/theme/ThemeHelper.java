package com.memento.android.assistlibrary.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;


public class ThemeHelper {

    public static final String PREF_KEY_THEME = "pref_key_theme_setting";


    /**
     * 获取当前Theme
     * @param context context
     * @return
     */
    public static MaterialTheme getCurrentTheme(Context context){
        int randomPos =  new Random().nextInt(MaterialTheme.getThemeList().size());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int pos = sharedPref.getInt(PREF_KEY_THEME, -1);
        if(pos == -1){
            sharedPref.edit().putInt(PREF_KEY_THEME, randomPos).apply();
            pos = randomPos;
        }
        return MaterialTheme.getThemeList().get(pos);
    }

    /**
     * 更新Theme
     * @param context context
     */
    public static void updateTheme(Context context){
        context.setTheme(getCurrentTheme(context).getThemeResId());
    }
}
