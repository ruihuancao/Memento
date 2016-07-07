package com.memento.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.memento.android.ui.main.MainActivity;
import com.memento.android.ui.preference.SettingsActivity;
import com.memento.android.ui.splash.SplashUrlActivity;
import com.memento.android.ui.zhihu.detail.ZhihuArticleDetailActivity;
import com.memento.android.ui.zhihu.main.ZhihuActivity;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class Navigator {

    @Inject
    public Navigator(){
    }

    public void openSplashActivity(Context mContext){
        Intent intent = new Intent(mContext, SplashUrlActivity.class);
        mContext.startActivity(intent);
    }


    public void openMainActivity(Context mContext){
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    public void openMainActivity(Activity activity, Pair<View, String>[] pairs){
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(activity, intent, pairs);
    }

    public void openSettingActivty(Context mContext){
        mContext.startActivity(new Intent(mContext, SettingsActivity.class));
    }

    public void openZhihuActivity(Context mContext){
        mContext.startActivity(new Intent(mContext, ZhihuActivity.class));
    }

    public void openZhihuDetailActivity(Activity activity, String id, Pair<View, String>[] pairs){
        Intent intent = new Intent(activity, ZhihuArticleDetailActivity.class);
        intent.putExtra(ZhihuArticleDetailActivity.PARAM_ONE, id);
        startActivity(activity, intent, pairs);
    }

    private void startActivity(Activity activity, Intent intent,  Pair<View, String>[] pairs) {
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(intent, transitionActivityOptions.toBundle());
    }

}  