package com.memento.android.ui.zhihu.splash;


import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.ui.animators.ZhihuSplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ZhihuSplashFragment extends BaseFragment implements ZhihuSplashContract.View{


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.title)
    TextView title;

    private Unbinder unbinder;
    private ZhihuSplashContract.Presenter mPresenter;

    public ZhihuSplashFragment() {
    }

    public static ZhihuSplashFragment newInstance() {
        return new ZhihuSplashFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(ZhihuSplashContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showImage(String url) {
        Glide.with(getContext()).load(url).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                ZhihuSplashAnimator zhihuSplashAnimator = new ZhihuSplashAnimator(imageView, title, new AnimatorEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        openZhihuMainActivity();
                    }
                });
                zhihuSplashAnimator.play();
            }
        });
    }

    @Override
    public void showError() {
        openZhihuMainActivity();
    }

    private void openZhihuMainActivity(){
        EventBus.getDefault().post(new Event.OpenZhihuMainActivity());
    }
}
