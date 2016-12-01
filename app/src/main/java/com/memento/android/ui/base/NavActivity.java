package com.memento.android.ui.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crh.android.common.login.LoginManager;
import com.crh.android.common.view.glide.GlideHelper;
import com.memento.android.BuildConfig;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.ui.douban.movie.DoubanMovieActivity;
import com.memento.android.ui.login.LoginActivity;
import com.memento.android.ui.setting.SettingsActivity;
import com.memento.android.ui.test.TestActivity;
import com.memento.android.ui.zhihu.main.ZhihuActivity;

import org.greenrobot.eventbus.Subscribe;


public class NavActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

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
                    if(LoginManager.isLogin(NavActivity.this)){
                        LoginManager.logout(NavActivity.this);
                    }else{
                        startActivity(LoginActivity.getCallIntent(NavActivity.this));
                    }
                }
            });
            final ImageView iconView = (ImageView) headView.findViewById(R.id.imageView);
            TextView nameView = (TextView) headView.findViewById(R.id.nameTextView);
            TextView desView = (TextView) headView.findViewById(R.id.desTextView);

            if(LoginManager.isLogin(this)){
                nameView.setText(LoginManager.getCurrentUser(this).getUsername());
                desView.setText(getString(R.string.user_des));
                GlideHelper.loadCircleResource(LoginManager.getCurrentUser(this).getAvatar(), iconView);
            }else{
                nameView.setText("未登录");
            }
        }
    }


    public void onNavigationItemClicked(int id){
        if (id == R.id.nav_zhihu) {
            startActivity(ZhihuActivity.getCallIntent(this));
        }else if(id == R.id.nav_douban_moive){
            startActivity(DoubanMovieActivity.getCallIntent(this));
        }
        else if (id == R.id.nav_dingxiang) {

        } else if (id == R.id.nav_wxjingxuan) {

        } else if (id == R.id.nav_send) {

        }else if(id  == R.id.nav_setting){
            startActivity(SettingsActivity.getCallIntent(this));
        }else if(id == R.id.nav_utils){
            startActivity(TestActivity.getCallIntent(this));
        }
    }

    private void updateNavMenu(){
        SharedPreferences sharedPref = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
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

            if(BuildConfig.DEBUG){
                mNavigationView.getMenu().findItem(R.id.nav_utils).setVisible(true);
            }else{
                mNavigationView.getMenu().findItem(R.id.nav_utils).setVisible(false);
            }
        }
    }

    @Subscribe
    public void onEvent(Event.UpdateNavMenuEvent event){
        updateNavMenu();
    }
}
