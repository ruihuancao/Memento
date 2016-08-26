package com.memento.android.ui.zhihu.splash;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.memento.android.R;
import com.memento.android.data.DataManager;
import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.ActivityUtils;
import com.memento.android.ui.zhihu.main.ZhihuActivity;
import com.memento.android.util.DensityUtil;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuSplashActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;

    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu_splash);
        ButterKnife.bind(this);

        ZhihuSplashFragment splashFragment =
                (ZhihuSplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (splashFragment == null) {
            splashFragment = ZhihuSplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.contentLayout);
        }
        new ZhihuSplashPresenter(mDataManager, splashFragment, DensityUtil.getScreenW(getApplicationContext()));
    }

    @Subscribe
    public void onEvent(Event.OpenMainActivityEvent event) {
        startActivity(ZhihuActivity.getCallIntent(this));
        finish();
    }
}

