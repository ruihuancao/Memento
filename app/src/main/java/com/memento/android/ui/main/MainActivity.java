package com.memento.android.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.memento.android.R;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.base.ActivityUtils;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.douban.movie.CommonMovieFragment;
import com.memento.android.ui.douban.movie.TheatersMovieFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RC_LOCATION_PERM = 121;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorlayout;
    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.bottom_navigation)
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
        initView();
        getPermission();
    }

    public static Intent getCallIntent(Context context){
        return new Intent(context, MainActivity.class);
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
                    ActivityUtils.showFloatingActionButton(floatingActionButton);
                } else {
                    ActivityUtils.hideFloatingActionButton(floatingActionButton);
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
    }

    private void initLocaltion(){

    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void getPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE };
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            //
            Logger.d("has all permissions");
        } else {
            Logger.d("request permissions");
            EasyPermissions.requestPermissions(this, getString(R.string.retionale_tip),
                    RC_LOCATION_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, perms);
    }
}
