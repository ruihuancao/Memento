package com.memento.android.ui.base;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jaeger.library.StatusBarUtil;
import com.memento.android.MementoApplication;
import com.memento.android.R;
import com.memento.android.data.repository.preference.SharePreferenceManager;
import com.memento.android.event.ShowMessageEvent;
import com.memento.android.event.UpdateNavMenuEvent;
import com.memento.android.injection.component.ActivityComponent;
import com.memento.android.injection.component.DaggerActivityComponent;
import com.memento.android.injection.module.ActivityModule;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.preference.SettingsActivity;
import com.memento.android.ui.theme.MaterialTheme;
import com.memento.android.util.MD5Util;

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
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        activityComponent().inject(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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

    private void updateTheme(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int pos = sharedPref.getInt(SettingsActivity.PREF_KEY_THEME, 0);
        setTheme(MaterialTheme.getThemeList().get(pos).getThemeResId());
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
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        updateNavMenu();
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            mDrawerLayout.closeDrawers();
                            onNavigationItemClicked(menuItem.getItemId());
                            return true;
                        }
                    });
            View headView = mNavigationView.inflateHeaderView(R.layout.nav_header_draw);
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            final ImageView iconView = (ImageView) headView.findViewById(R.id.imageView);
            TextView nameView = (TextView) headView.findViewById(R.id.nameTextView);
            TextView desView = (TextView) headView.findViewById(R.id.desTextView);
            String url = "https://www.gravatar.com/avatar/"+ MD5Util.md5Hex("ruihuancao@gmail.com");
            Glide.with(this).load(url).asBitmap().fitCenter().into(new BitmapImageViewTarget(iconView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    iconView.setImageDrawable(circularBitmapDrawable);
                }
            });
            Glide.with(this).load(url).into(iconView);
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
        }else if(id == R.id.nav_douban_moive){
            mNavigator.openDoubanMoiveActivity(this);
        }
        else if (id == R.id.nav_dingxiang) {

        } else if (id == R.id.nav_wxjingxuan) {

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

    private void updateNavMenu(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isShow_dxys = sharedPref.getBoolean(SettingsActivity.FUNCTION_DXYS_KEY, true);
        boolean isShow_douban_movie = sharedPref.getBoolean(SettingsActivity.FUNCTION_DOUBAN_MOIVE_KEY, true);
        boolean isShow_douban_book = sharedPref.getBoolean(SettingsActivity.FUNCTION_DOUBAN_BOOK_KEY, true);
        boolean isShow_weixin = sharedPref.getBoolean(SettingsActivity.FUNCTION_WEIXIN_KEY, true);
        boolean isShow_zhihu = sharedPref.getBoolean(SettingsActivity.FUNCTION_ZHIHU_KEY, true);
        boolean isShow_meitu = sharedPref.getBoolean(SettingsActivity.FUNCTION_MEITU_KEY, true);
        if(mNavigationView != null){
            mNavigationView.getMenu().findItem(R.id.nav_douban_moive).setVisible(isShow_douban_movie);
            mNavigationView.getMenu().findItem(R.id.nav_douban_book).setVisible(isShow_douban_book);
            mNavigationView.getMenu().findItem(R.id.nav_dingxiang).setVisible(isShow_dxys);
            mNavigationView.getMenu().findItem(R.id.nav_wxjingxuan).setVisible(isShow_weixin);
            mNavigationView.getMenu().findItem(R.id.nav_zhihu).setVisible(isShow_zhihu);
            mNavigationView.getMenu().findItem(R.id.nav_meitu).setVisible(isShow_meitu);
        }
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

    @Subscribe
    public void onEvent(UpdateNavMenuEvent event){
        updateNavMenu();
    }

}