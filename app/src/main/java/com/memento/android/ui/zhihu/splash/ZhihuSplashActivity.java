package com.memento.android.ui.zhihu.splash;

import android.animation.Animator;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.data.entity.SplashImageEntity;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.ZhihuSplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.util.DensityUtil;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ZhihuSplashActivity extends BaseActivity{

    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.title)
    TextView mTitle;

    @Inject
    Repository mRepository;
    @Inject
    Navigator mNavigator;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu_splash);
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
                }).subscribe(new DefaultSubscriber<String>(){

                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        Glide.with(ZhihuSplashActivity.this).load(s).into(new GlideDrawableImageViewTarget(mImageView) {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                ZhihuSplashAnimator zhihuSplashAnimator = new ZhihuSplashAnimator(mImageView, mTitle, new AnimatorEndListener() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mNavigator.openZhihuActivity(ZhihuSplashActivity.this);
                                        finish();
                                    }
                                });
                                zhihuSplashAnimator.play();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mNavigator.openZhihuActivity(ZhihuSplashActivity.this);
                        finish();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription != null && mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
