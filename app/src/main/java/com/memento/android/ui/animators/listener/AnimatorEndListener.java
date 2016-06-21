package com.memento.android.ui.animators.listener;

import android.animation.Animator;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-04-01
 * Time: 16:08
 *
 */
public abstract class AnimatorEndListener implements Animator.AnimatorListener{
    public void onAnimationStart(Animator animation){};
    public abstract void onAnimationEnd(Animator animation);
    public void onAnimationCancel(Animator animation) {}
    public void onAnimationRepeat(Animator animation) {}
}  