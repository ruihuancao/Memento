package com.memento.android.ui.splash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.memento.android.R;
import com.memento.android.data.DataManager;

import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.main.MainActivity;
import com.memento.android.ui.utils.ActivityUtils;
import com.memento.android.util.DensityUtil;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashUrlActivity extends BaseActivity {


    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;
    @Inject
    DataManager mDataManager;

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash_url);
        ButterKnife.bind(this);
        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.contentLayout);
        }
        mSplashPresenter = new SplashPresenter(mDataManager, splashFragment, DensityUtil.getScreenW(getApplicationContext()));
    }


    @Subscribe
    public void onEvent(Event.OpenMainActivityEvent event) {
        startActivity(MainActivity.getCallIntent(this));
        finish();
    }
}
