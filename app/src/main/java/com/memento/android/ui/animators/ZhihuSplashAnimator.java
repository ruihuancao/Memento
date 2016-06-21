package com.memento.android.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.ui.animators.listener.AnimatorStartListener;


public class ZhihuSplashAnimator {

    private AnimatorSet animator;
    private View imageView;
    private TextView textView;
    private Animator.AnimatorListener mAnimatorListener;

    public ZhihuSplashAnimator(ImageView imageView, TextView textView, Animator.AnimatorListener animatorListener){
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
        //animator.play(imageAnimator).before(textAnimator);
        //animator.play(imageAnimator).after(1000).after(textAnimator);
        animator.playTogether(imageAnimator, textAnimator);
    }

    private Animator getImageViewAnimator(){
        AnimatorSet animator = new AnimatorSet();
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 1.0f, 1.2f, 1.0f);
        animator.playTogether(scaleXAnimator, scaleYAnimator);
        return animator;
    }

    private Animator getTextViewAnimator(){
        AnimatorSet animator = new AnimatorSet();
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(textView, View.TRANSLATION_Y, -(textView.getTop()), 0f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(textView, View.ALPHA, 0.5f, 1.0f);

        ObjectAnimator alphaYAnimator = ObjectAnimator.ofFloat(textView, View.ALPHA, 0f, 1f);


        translationYAnimator.addListener(new AnimatorStartListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.setVisibility(View.VISIBLE);
            }
        });
        animator.playTogether(translationYAnimator, alphaAnimator);
        return alphaYAnimator;
    }

    public void play() {
        animator.start();
    }
}  