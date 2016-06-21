package com.memento.android.injection.component;


import com.memento.android.injection.PerActivity;
import com.memento.android.injection.module.ActivityModule;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.main.HomeFragment;
import com.memento.android.ui.main.MainActivity;
import com.memento.android.ui.main.MainFragment;
import com.memento.android.ui.splash.SplashActivity;
import com.memento.android.ui.splash.SplashUrlActivity;
import com.memento.android.ui.zhihu.detail.ZhihuArticleDetailActivity;
import com.memento.android.ui.zhihu.main.ZhihuActivity;
import com.memento.android.ui.zhihu.main.ZhihuMainFragment;
import com.memento.android.ui.zhihu.splash.ZhihuSplashActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
    void inject(SplashUrlActivity splashUrlActivity);
    void inject(MainActivity mainActivity);
    void inject(SplashActivity splashActivity);

    void inject(HomeFragment homeFragment);
    void inject(MainFragment mainFragment);

    void inject(ZhihuActivity zhihuActivity);
    void inject(ZhihuSplashActivity zhihuSplashActivity);
    void inject(ZhihuArticleDetailActivity zhihuArticleDetailActivity);
    void inject(ZhihuMainFragment mainFragment);
}

