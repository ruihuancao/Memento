package com.memento.android.ui.splash;

import android.animation.Animator;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.SplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity{

    @Bind(R.id.splash_image)
    ImageView mSplashImage;
    @Bind(R.id.splash_text)
    TextView mSplashText;

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashAnimator splashAnimator = new SplashAnimator(mSplashImage, mSplashText, new AnimatorEndListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mNavigator.openMainActivity(SplashActivity.this);
                finish();
            }
        });
        splashAnimator.play();
    }
}
