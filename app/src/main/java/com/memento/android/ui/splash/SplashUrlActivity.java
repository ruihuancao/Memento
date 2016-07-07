package com.memento.android.ui.splash;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.memento.android.data.Repository;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.SplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.util.DensityUtil;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SplashUrlActivity extends BaseActivity{

    @Bind(R.id.fullscreen_imageview)
    ImageView fullscreenImageview;
    @Bind(R.id.contenttext)
    LinearLayout contenttext;
    @Bind(R.id.text)
    TextView text;

    @Inject
    Navigator mNavigator;
    @Inject
    Repository mRepository;

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash_url);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        mSubscription = mRepository.getImageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<List<SplashImageEntity>, String>() {
                    @Override
                    public String call(List<SplashImageEntity> splashImageEntities) {
                        int random  = new Random().nextInt(splashImageEntities.size());
                        return splashImageEntities.get(random).getPhotoUrl(DensityUtil.getScreenW(getApplicationContext()));
                    }
                }).subscribe(new DefaultSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        Logger.d(s);
                        Glide.with(SplashUrlActivity.this).load(s).asBitmap().into(new BitmapImageViewTarget(fullscreenImageview) {
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

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                mNavigator.openMainActivity(SplashUrlActivity.this);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                        mNavigator.openMainActivity(SplashUrlActivity.this);
                        finish();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }
}
