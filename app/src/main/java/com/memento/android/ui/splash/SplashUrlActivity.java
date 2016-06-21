package com.memento.android.ui.splash;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.memento.android.R;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.SplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.util.DensityUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SplashUrlActivity extends BaseActivity implements SplashContract.View {

    @Bind(R.id.fullscreen_imageview)
    ImageView fullscreenImageview;
    @Bind(R.id.contenttext)
    LinearLayout contenttext;
    @Bind(R.id.text)
    TextView text;

    @Inject
    Navigator mNavigator;

    @Inject
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash_url);
        ButterKnife.bind(this);
        splashPresenter.attachView(this);
        splashPresenter.getImage(DensityUtil.getScreenW(getApplicationContext()));
    }

    @Override
    public void showImage(String url) {
        Logger.d(url);
        Glide.with(this).load(url).asBitmap().into(new BitmapImageViewTarget(fullscreenImageview) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch s1 = palette.getVibrantSwatch();
                        /*s2 = palette.getDarkVibrantSwatch();
                        s3 = palette.getLightVibrantSwatch();
                        s4 = palette.getMutedSwatch();
                        s5 = palette.getDarkMutedSwatch();
                        s6 = palette.getLightMutedSwatch();*/
                        if (s1 != null) {
                            contenttext.setBackgroundColor(s1.getRgb());
                        }
                        contenttext.setVisibility(View.VISIBLE);
                    }
                });
                SplashAnimator splashAnimator = new SplashAnimator(fullscreenImageview, text, new AnimatorEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mNavigator.openMainActivity(SplashUrlActivity.this);
                        finish();
                    }
                });
                splashAnimator.play();
            }
        });
    }
}
