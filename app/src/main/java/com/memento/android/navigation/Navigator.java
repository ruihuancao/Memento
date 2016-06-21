package com.memento.android.navigation;

import android.content.Context;
import android.content.Intent;

import com.memento.android.leancloudlibrary.login.LearnCloudLoginActivity;
import com.memento.android.ui.main.MainActivity;
import com.memento.android.ui.preference.SettingsActivity;
import com.memento.android.ui.zhihu.main.ZhihuActivity;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class Navigator {

    @Inject
    public Navigator(){
    }

    public void openMainActivity(Context mContext){
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    public void openSettingActivty(Context mContext){
        mContext.startActivity(new Intent(mContext, SettingsActivity.class));
    }

    public void openZhihuMainActivity(Context mContext){
        mContext.startActivity(ZhihuActivity.getCallingIntent(mContext));
    }

    public void openLoginActivity(Context mContext){
        mContext.startActivity(new Intent(mContext, LearnCloudLoginActivity.class));
    }

    public void openTestPage(Context mContext){
        Intent intent = new Intent(mContext, LearnCloudLoginActivity.class);
        mContext.startActivity(intent);
    }
}  