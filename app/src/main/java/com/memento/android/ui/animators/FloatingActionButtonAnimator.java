package com.memento.android.ui.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by android on 16-6-13.
 */
public class FloatingActionButtonAnimator {

    private FloatingActionButton floatingActionButton;
    private Animator.AnimatorListener mAnimatorListener;

    private Animator animator;
    private boolean flag;

    public FloatingActionButtonAnimator(FloatingActionButton floatingActionButton, boolean flag) {
        this(floatingActionButton, null, flag);
    }

    public FloatingActionButtonAnimator(FloatingActionButton floatingActionButton, Animator.AnimatorListener animatorListener, boolean flag) {
        this.floatingActionButton = floatingActionButton;
        this.mAnimatorListener = animatorListener;
        this.flag = flag;
        initializeAnimator();
    }

    private void initializeAnimator() {
        if(flag){
            animator = getButtonShowAnimator();
        }else{
            animator = getButtonHideAnimator();
        }
        if(mAnimatorListener != null){
            animator.addListener(mAnimatorListener);
        }
    }

    private Animator getButtonShowAnimator(){
        AnimatorSet animator = new AnimatorSet();
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.ALPHA, 0f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_X, 0f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_Y, 0f, 1.0f);
        animator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        return animator;
    }

    private Animator getButtonHideAnimator(){
        AnimatorSet animator = new AnimatorSet();
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.ALPHA, 1.0f, 0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_X, 1.0f, 0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_Y, 1.0f, 0f);
        animator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        return animator;
    }

    public void play() {
        animator.start();
    }
}
