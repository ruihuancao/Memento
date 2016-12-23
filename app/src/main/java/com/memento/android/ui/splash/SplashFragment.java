package com.memento.android.ui.splash;


import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.memento.android.helper.GlideHelper;
import com.memento.android.R;
import com.memento.android.ui.animators.SplashAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;


public class SplashFragment extends BaseFragment implements SplashContract.View {

    @BindView(R.id.fullscreen_imageview)
    ImageView fullscreenImageview;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.contenttext)
    LinearLayout contenttext;

    private SplashContract.Presenter mPresenter;
    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SplashFragment.OnFragmentInteractionListener) {
            mListener = (SplashFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        GlideHelper.loadBitmap(url, new BitmapImageViewTarget(fullscreenImageview) {
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
        mListener.openMainActivity();
    }

    interface OnFragmentInteractionListener{
        void openMainActivity();
    }

}
