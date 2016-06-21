package com.memento.android.ui.animators.listener;

import android.animation.Animator;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-04-01
 * Time: 16:06
 * FIXME
 */
public abstract class AnimatorStartListener implements Animator.AnimatorListener {

    public abstract void onAnimationStart(Animator animation);
    public void onAnimationEnd(Animator animation){};
    public void onAnimationCancel(Animator animation) {}
    public void onAnimationRepeat(Animator animation) {}
}  