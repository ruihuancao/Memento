package com.memento.android.ui.splash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.memento.android.R;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.main.MainActivity;
import com.memento.android.util.ActivityUtils;
import com.memento.android.util.DensityUtil;

import butterknife.ButterKnife;


public class SplashUrlActivity extends BaseActivity implements SplashFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_url);
        ButterKnife.bind(this);
        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.contentLayout);
        }
        new SplashPresenter(splashFragment, DensityUtil.getScreenW(getApplicationContext()));
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getCallIntent(this));
        finish();
    }
}
