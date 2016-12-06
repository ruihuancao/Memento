package com.memento.android.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.crh.android.common.data.download.DownLoad;
import com.crh.android.common.subscriber.DefaultSubscriber;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.helper.DataHelper;
import com.memento.android.ui.animators.FloatingActionButtonAnimator;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.ui.base.NavActivity;
import com.memento.android.ui.douban.movie.CommonMovieFragment;
import com.memento.android.ui.search.SearchActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends NavActivity{

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

    private MainViewPagerAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        getPermission();

        DataHelper.provideDataManager(getApplicationContext())
                .getDownloadManager()
                .downLoadFile("http://application-release.guokr.com/v3400Mentor-release-QDHome.apk?t=1480916745")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<DownLoad>(){

                    @Override
                    public void onNext(DownLoad downLoad) {
                        super.onNext(downLoad);
                        Logger.d("progress: "+downLoad.getProgress());
                        if(downLoad.isDone()){
                            Logger.d("file:"+downLoad.getFilePath());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // your logic
                startActivity(SearchActivity.getCallIntent(MainActivity.this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static Intent getCallIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    private void initView(){
        setSupportActionBar(mToolbar);
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
                    floatingActionButton.setVisibility(View.VISIBLE);
                    new FloatingActionButtonAnimator(floatingActionButton, true).play();
                } else {
                    new FloatingActionButtonAnimator(floatingActionButton, false).play();
                }
            }
        });

        viewPager.setOffscreenPageLimit(4);
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(MainFragment.newInstance());
        fragments.add(PeopleFragment.newInstance());
        fragments.add(CommonMovieFragment.newInstance(CommonMovieFragment.COMINGSOON_TYPE));
        fragments.add(CommonMovieFragment.newInstance(CommonMovieFragment.TOP250_TYPE));
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Subscribe
    public void onEvent(Event.LocaltionResultEvent event) {
        Snackbar.make(bottomNavigation, "定位成功", Snackbar.LENGTH_SHORT).show();
    }
}
