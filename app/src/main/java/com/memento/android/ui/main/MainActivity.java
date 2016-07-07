package com.memento.android.ui.main;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.FloatingActionButtonAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.douban.movie.CommonMovieFragment;
import com.memento.android.ui.douban.movie.TestFragment;
import com.memento.android.ui.douban.movie.TheatersMovieFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorlayout;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @Bind(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Inject
    Navigator mNavigator;

    @Inject
    Repository mRepository;

    private Interpolator interpolator;
    private MainViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        ButterKnife.bind(this);
        initView();
    }

    private void setupWindowAnimations() {
        setupEnterAnimations();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                // Removing listener here is very important because shared element transition is executed again backwards on exit. If we don't remove the listener this code will be triggered again.
                transition.removeListener(this);
                hideTarget();
                animateRevealShow(mToolbar);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void hideTarget(){
        findViewById(R.id.shared_target).setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void initView(){
        initBottomView();
    }


    private void initBottomView(){

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
        int colorPrimaryRes = typedValue.resourceId;

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.nav_home, R.drawable.ic_home_black_24dp, colorPrimaryRes);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.nav_collection, R.drawable.ic_collections_black_24dp, colorPrimaryRes);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.nav_friends, R.drawable.ic_people_black_24dp, colorPrimaryRes);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.nav_person, R.drawable.ic_person_black_24dp, colorPrimaryRes);

        bottomNavigationItems = new ArrayList<>();
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setAccentColor(getResources().getColor(colorPrimaryRes));
        bottomNavigation.setNotificationBackgroundColorResource(colorPrimaryRes);
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.secondary_text));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, false);
                if (position == 1) {
                    bottomNavigation.setNotification("", 1);
                    showFloatingActionButton();
                } else {
                    hideFloatingActionButton();
                }
            }
        });

        viewPager.setOffscreenPageLimit(4);
        ArrayList<BaseFragment> fragments = new ArrayList<>();

        fragments.add(TheatersMovieFragment.newInstance());
        fragments.add(CommonMovieFragment.newInstance(CommonMovieFragment.COMINGSOON_TYPE));
        fragments.add(CommonMovieFragment.newInstance(CommonMovieFragment.TOP250_TYPE));
        fragments.add(TestFragment.newInstance());
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomNavigation.setNotification("16", 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();
            }
        }, 3000);
    }


    private void showFloatingActionButton(){
        floatingActionButton.setVisibility(View.VISIBLE);
        FloatingActionButtonAnimator actionButtonAnimator = new FloatingActionButtonAnimator(floatingActionButton, true);
        actionButtonAnimator.play();
    }

    private void hideFloatingActionButton(){
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
