package com.memento.android.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.ui.animators.listener.AnimatorStartListener;

public class SplashAnimator {

    private ImageView imageView;
    private TextView textView;
    private Animator.AnimatorListener mAnimatorListener;
    private AnimatorSet animator;


    public SplashAnimator(ImageView imageView, TextView textView, Animator.AnimatorListener animatorListener){
        this.imageView = imageView;
        this.textView = textView;
        this.mAnimatorListener = animatorListener;
        initializeAnimator();
    }

    private void initializeAnimator() {
        Animator imageAnimator = getImageViewAnimator();
        Animator textAnimator = getTextViewAnimator();
        animator = new AnimatorSet();
        animator.addListener(mAnimatorListener);
        animator.playTogether(imageAnimator, textAnimator);
    }

    private Animator getImageViewAnimator(){
        ObjectAnimator alphaYAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f, 1f);
        alphaYAnimator.setDuration(2000);
        alphaYAnimator.setInterpolator(new LinearInterpolator());
        alphaYAnimator.addListener(new AnimatorStartListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView.setVisibility(View.VISIBLE);
            }
        });
        return alphaYAnimator;
    }

    private Animator getTextViewAnimator(){
        ObjectAnimator alphaYAnimator = ObjectAnimator.ofFloat(textView, View.ALPHA, 0f, 1f);
        alphaYAnimator.setDuration(2000);
        alphaYAnimator.setInterpolator(new LinearInterpolator());
        alphaYAnimator.addListener(new AnimatorStartListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.setVisibility(View.VISIBLE);
            }
        });
        return alphaYAnimator;
    }

    public void play() {
        animator.start();
    }

}  