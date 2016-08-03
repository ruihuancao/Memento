package com.memento.android.ui.main;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

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
import com.memento.android.ui.douban.movie.TheatersMovieFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorlayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Inject
    Navigator mNavigator;
    @Inject
    Repository mRepository;

    private MainViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(mToolbar);
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
        fragments.add(PeopleFragment.newInstance());
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        Observable.interval(3000, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                bottomNavigation.setNotification("16", 1);
                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
                        Snackbar.LENGTH_SHORT).show();
            }
        });
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
