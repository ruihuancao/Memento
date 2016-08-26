package com.memento.android.ui.splash;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.ui.animators.SplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.main.MainActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends BaseFragment implements SplashContract.View {

    @BindView(R.id.fullscreen_imageview)
    ImageView fullscreenImageview;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.contenttext)
    LinearLayout contenttext;

    private SplashContract.Presenter mPresenter;
    private Unbinder unbinder;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showImage(String url) {
        Glide.with(getContext()).load(url).asBitmap().
                into(new BitmapImageViewTarget(fullscreenImageview) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch s1 = palette.getVibrantSwatch();
                                if (s1 != null) {
                                    contenttext.setBackgroundColor(s1.getRgb());
                                }
                                contenttext.setVisibility(View.VISIBLE);
                            }
                        });
                        SplashAnimator splashAnimator = new SplashAnimator(fullscreenImageview,
                                text, new AnimatorEndListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                openMainActivity();
                            }
                        });
                        splashAnimator.play();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        openMainActivity();
                    }
                });
    }

    @Override
    public void showError() {
        openMainActivity();
    }

    private void openMainActivity(){
        EventBus.getDefault().post(new Event.OpenMainActivityEvent());
    }

}
