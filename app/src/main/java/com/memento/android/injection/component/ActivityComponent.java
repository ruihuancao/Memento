package com.memento.android.injection.component;


import com.memento.android.injection.PerActivity;
import com.memento.android.injection.module.ActivityModule;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.douban.movie.CommonMovieFragment;
import com.memento.android.ui.douban.movie.TheatersMovieFragment;
import com.memento.android.ui.main.HomeFragment;
import com.memento.android.ui.main.MainActivity;
import com.memento.android.ui.main.PhotoFragment;
import com.memento.android.ui.preference.SettingsActivity;
import com.memento.android.ui.splash.SplashUrlActivity;
import com.memento.android.ui.theme.ThemeSettingActivity;
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
    void inject(ThemeSettingActivity themeSettingActivity);
    void inject(SettingsActivity settingsActivity);

    void inject(HomeFragment homeFragment);
    void inject(PhotoFragment photoFragment);

    void inject(BaseFragment baseFragment);
    void inject(TheatersMovieFragment theatersMovieFragment);
    void inject(CommonMovieFragment commonMovieFragment);

    void inject(ZhihuActivity zhihuActivity);
    void inject(ZhihuSplashActivity zhihuSplashActivity);
    void inject(ZhihuArticleDetailActivity zhihuArticleDetailActivity);
    void inject(ZhihuMainFragment mainFragment);
}

