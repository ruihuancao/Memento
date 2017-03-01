package com.memento.android.ui.usage;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.memento.android.R;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.util.ActivityUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsageStatisticsActivity extends BaseActivity {

    UsageStatsManager mUsageStatsManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_statistics);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        if (getUsageStatistics(UsageStatsManager.INTERVAL_DAILY).size() == 0) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            //print();
        }
        UsageStatisicsFragment usageStatisicsFragment =
                (UsageStatisicsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (usageStatisicsFragment == null) {
            usageStatisicsFragment = UsageStatisicsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), usageStatisicsFragment, R.id.container);
        }
    }

    public static Intent getCallIntent(Context context) {
        return new Intent(context, UsageStatisticsActivity.class);
    }


    public void print() {
        List<UsageStats> list = getUsageStatistics(UsageStatsManager.INTERVAL_WEEKLY);
        UsageEvents usageEvents = getUsageEvents();
        Map<String, CustomUsageStats> data = update(list, usageEvents);
        Log.d("Test", "size:" + data.size());
        for (Map.Entry<String, CustomUsageStats> entry : data.entrySet()) {
            Log.e("Test", entry.getKey() + "-- lunchtime:" + entry.getValue().lunchTime + "-- syslunchtime:" + entry.getValue().systemLunchTime
                    + "--  totalTime:" + entry.getValue().totalTime / (1000 * 60));
        }
    }

    public UsageEvents getUsageEvents() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        return mUsageStatsManager.queryEvents(cal.getTimeInMillis(), System.currentTimeMillis());
    }

    public List<UsageStats> getUsageStatistics(int intervalType) {
        // Get the app statistics since one year ago from the current time.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());
        return queryUsageStats;
    }

    public Map<String, CustomUsageStats> update(List<UsageStats> list, UsageEvents events) {
        Map<String, CustomUsageStats> data = new HashMap<>();
        CustomUsageStats customUsageStats;
        for (UsageStats usageStats : list) {
            customUsageStats = new CustomUsageStats();
            try {
                customUsageStats.lastTimeStamp = usageStats.getLastTimeStamp();
                customUsageStats.packageName = usageStats.getPackageName();
                customUsageStats.totalTime = usageStats.getTotalTimeInForeground();
                Field mLaunchCount = UsageStats.class.getDeclaredField("mLaunchCount");
                int launchCount = (Integer) mLaunchCount.get(usageStats);
                customUsageStats.systemLunchTime = launchCount;
                Drawable appIcon = getPackageManager()
                        .getApplicationIcon(usageStats.getPackageName());
                customUsageStats.appIcon = appIcon;
                if (customUsageStats.packageName.contains("com.google.android") || customUsageStats.packageName.contains("com.android.providers")) {
                    continue;
                }
                data.put(customUsageStats.packageName, customUsageStats);
            } catch (PackageManager.NameNotFoundException e) {
                customUsageStats.appIcon = getDrawable(android.R.drawable.sym_def_app_icon);
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }

        UsageEvents.Event event = null;
        UsageEvents.Event perevent = null;

        List<UsageEvents.Event> eventList = new ArrayList<>();
        while (events.hasNextEvent()) {
            event = new UsageEvents.Event();
            events.getNextEvent(event);
//            Log.d("Test", "EventType:"+event.getEventType()
//                        +"-- ClassName:"+event.getClassName()+"--getTimeStamp:"+event.getTimeStamp());
            if (perevent != null && perevent.getClassName() != null && perevent.getClassName().contains("launcher") && perevent.getEventType() == 2) {
                if (event.getEventType() == 1) {
                    CustomUsageStats customUsageStats1 = data.get(event.getPackageName());
                    if (customUsageStats1 != null) {
                        customUsageStats1.lunchTime++;
                    }
                }
            }
            perevent = event;
        }
        return data;
    }
}
