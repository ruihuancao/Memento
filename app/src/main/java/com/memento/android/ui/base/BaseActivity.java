package com.memento.android.ui.base;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.memento.android.MementoApplication;
import com.memento.android.R;
import com.memento.android.data.repository.preference.SharePreferenceManager;
import com.memento.android.event.ShowMessageEvent;
import com.memento.android.injection.component.ActivityComponent;
import com.memento.android.injection.component.DaggerActivityComponent;
import com.memento.android.injection.module.ActivityModule;
import com.memento.android.navigation.Navigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;


public class BaseActivity extends AppCompatActivity {


    protected ActivityComponent mActivityComponent;

    @Inject
    Navigator mNavigator;
    @Inject
    SharePreferenceManager mSharePreferenceManager;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent().inject(this);
        setTheme(mSharePreferenceManager.getCurrentTheme().getThemeResId());
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //setStatusBar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void setupNavDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mDrawerLayout == null || mToolbar == null) {
            return;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            mDrawerLayout.closeDrawers();
                            onNavigationItemClicked(menuItem.getItemId());
                            return true;
                        }
                    });
            View headView = navigationView.inflateHeaderView(R.layout.nav_header_draw);
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ImageView iconView = (ImageView) headView.findViewById(R.id.imageView);
            TextView nameView = (TextView) headView.findViewById(R.id.nameTextView);
            TextView desView = (TextView) headView.findViewById(R.id.desTextView);
        }
    }


    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MementoApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }


    public void onNavigationItemClicked(int id){
        if (id == R.id.nav_zhihu) {
            mNavigator.openZhihuActivity(this);
        } else if (id == R.id.nav_dingxiang) {

        } else if (id == R.id.nav_wxjingxuan) {

        } else if (id == R.id.nav_joke) {

        } else if (id == R.id.nav_pic) {

        } else if (id == R.id.nav_send) {

        }else if(id  == R.id.nav_setting){
            mNavigator.openSettingActivty(this);
        }
    }

    protected void setStatusBar() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        StatusBarUtil.setColor(this, typedValue.data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(ShowMessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

}