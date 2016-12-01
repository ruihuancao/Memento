package com.crh.android.common.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.crh.android.common.util.MD5Util;

/**
 * Created by caoruihuan on 16/9/29.
 */

public class LoginManager {

    public static final String PREF_KEY_THEME = "pref_key_login_setting";

    /**
     * 判断是否登录
     * @param context
     * @return
     */
    public static boolean isLogin(Context context){
        return getCurrentUser(context) != null;
    }

    /**
     * 保存用户信息
     * @param context
     * @param leanCloudUserEntiry
     * @return
     */
    public static boolean login(Context context, LeanCloudUserEntiry leanCloudUserEntiry){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putString(PREF_KEY_THEME, new Gson().toJson(leanCloudUserEntiry)).apply();
        return true;
    }


    public static String getDefaultAvatar(String mail){
        return "https://www.gravatar.com/avatar/"+ MD5Util.md5Hex(mail);
    }

    /**
     * 推出登录
     * @param context
     * @return
     */
    public static boolean logout(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPref.edit().putString(PREF_KEY_THEME, "").apply();
        return true;
    }

    /**
     * 获取当前用户
     * @param context
     * @return
     */
    public static LeanCloudUserEntiry getCurrentUser(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String data = sharedPref.getString(PREF_KEY_THEME, "");
        if(TextUtils.isEmpty(data)){
            return null;
        }
        try{
            return new Gson().fromJson(data, LeanCloudUserEntiry.class);
        }catch (Exception e){
            return null;
        }
    }
}
