package com.memento.android.ui.main;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.leancloudlibrary.login.LearnCloud;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.animators.FloatingActionButtonAnimator;
import com.memento.android.ui.animators.listener.AnimatorEndListener;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.widget.CropCircleTransformation;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener{

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

    private MainViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initHeadView();
        initDrawView();
        initBottomView();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        onNavItemSelected(item);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initHeadView() {
        mNavView.setNavigationItemSelectedListener(this);
        View headView = mNavView.inflateHeaderView(R.layout.nav_header_draw);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LearnCloud.isLogin()) return;
                mNavigator.openLoginActivity(MainActivity.this);
            }
        });
        ImageView iconView = (ImageView) headView.findViewById(R.id.imageView);
        TextView nameView = (TextView) headView.findViewById(R.id.nameTextView);
        TextView desView = (TextView) headView.findViewById(R.id.desTextView);
        if(LearnCloud.isLogin() && LearnCloud.getUserIcon() != null){
            Glide.with(this).load(LearnCloud.getUserIcon())
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(iconView);
        }
        nameView.setText(LearnCloud.getUserName());
    }

    private void initDrawView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initBottomView(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.nav_home, R.drawable.ic_home_black_24dp, R.color.primary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.nav_collection, R.drawable.ic_collections_black_24dp, R.color.primary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.nav_friends, R.drawable.ic_people_black_24dp, R.color.primary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.nav_person, R.drawable.ic_person_black_24dp, R.color.primary);

        bottomNavigationItems = new ArrayList<>();
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setForceTitlesDisplay(true);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.primary));
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

        fragments.add(MainFragment.newInstance());
        fragments.add(CollectionFragment.newInstance());
        fragments.add(PeopleFragment.newInstance());
        fragments.add(PeosonFragment.newInstance());
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
