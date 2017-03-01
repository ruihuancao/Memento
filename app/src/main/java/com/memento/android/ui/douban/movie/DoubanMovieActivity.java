package com.memento.android.ui.douban.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.memento.android.R;
import com.memento.android.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubanMovieActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout mCoordinatorlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_movie);
        ButterKnife.bind(this);
        initView();
    }

    public static Intent getCallIntent(Context context){
        return new Intent(context, DoubanMovieActivity.class);
    }

    private void initView(){
        setSupportActionBar(mToolbar);
        setupViewPager();
        mTabs.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(){
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(TheatersMovieFragment.newInstance(), "正在上映");
        adapter.addFragment(CommonMovieFragment.newInstance(CommonMovieFragment.TOP250_TYPE), "Top250");
        adapter.addFragment(CommonMovieFragment.newInstance(CommonMovieFragment.COMINGSOON_TYPE), "即将上映");
        mViewPager.setAdapter(adapter);
    }



    static class Adapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager manager){
            super(manager);
        }

        public void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
