package com.memento.android.ui.zhihu.splash;

import android.animation.Animator;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.memento.android.R;
import com.memento.android.ui.animators.ZhihuSplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.zhihu.main.ZhihuActivity;
import com.memento.android.util.DensityUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZhihuSplashActivity extends BaseActivity implements ZhihuSplashContract.View {

    @Bind(R.id.imageView)
    ImageView mImageView;
    @Bind(R.id.title)
    TextView mTitle;

    @Inject
    ZhihuSplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu_splash);
        ButterKnife.bind(this);
        mSplashPresenter.attachView(this);
        mSplashPresenter.getImage(DensityUtil.getScreenW(getApplicationContext())+"*"+DensityUtil.getScreenH(getApplicationContext()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.detachView();
    }

    @Override
    public void showImage(String imageUrl) {
        Glide.with(ZhihuSplashActivity.this).load(imageUrl).into(new GlideDrawableImageViewTarget(mImageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                showAnimation();
            }
        });
    }

    @Override
    public void showError() {
        startActivity(ZhihuActivity.getCallingIntent(ZhihuSplashActivity.this));
    }

    private void showAnimation() {
        ZhihuSplashAnimator zhihuSplashAnimator = new ZhihuSplashAnimator(mImageView, mTitle, new AnimatorEndListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(ZhihuActivity.getCallingIntent(ZhihuSplashActivity.this));
            }
        });
        zhihuSplashAnimator.play();
    }
}
