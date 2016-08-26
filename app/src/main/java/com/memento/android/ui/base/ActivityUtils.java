package com.memento.android.ui.base;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.memento.android.ui.animators.FloatingActionButtonAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;

import static com.google.common.base.Preconditions.checkNotNull;


public class ActivityUtils {

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void showFloatingActionButton(final FloatingActionButton floatingActionButton){
        floatingActionButton.setVisibility(View.VISIBLE);
        FloatingActionButtonAnimator actionButtonAnimator = new FloatingActionButtonAnimator(floatingActionButton, true);
        actionButtonAnimator.play();
    }

    public static void hideFloatingActionButton(final FloatingActionButton floatingActionButton){
        if (floatingActionButton.getVisibility() == View.VISIBLE) {
            FloatingActionButtonAnimator actionButtonAnimator = new FloatingActionButtonAnimator(floatingActionButton, new AnimatorEndListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    floatingActionButton.setVisibility(View.GONE);
                }
            }, false);
            actionButtonAnimator.play();
        }
    }
}
