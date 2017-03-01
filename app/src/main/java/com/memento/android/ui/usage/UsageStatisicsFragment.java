package com.memento.android.ui.usage;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.ui.base.BaseFragment;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UsageStatisicsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    UsageStatsManager mUsageStatsManager;

    private DateFormat mDateFormat = new SimpleDateFormat();

    private Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public UsageStatisicsFragment() {
        // Required empty public constructor
    }

    public static UsageStatisicsFragment newInstance() {
        return new UsageStatisicsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usage_statisics, container, false);
        ButterKnife.bind(this, view);
        mUsageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<UsageStats> list = getUsageStatistics(UsageStatsManager.INTERVAL_DAILY);
        Log.d("Test", "list size:"+list.size());
        UsageEvents usageEvents = getUsageEvents();
        mAdapter = new Adapter(update(list, usageEvents));
        layoutManager = recyclerview.getLayoutManager();
        recyclerview.scrollToPosition(0);
        recyclerview.setAdapter(mAdapter);
    }


    class Adapter extends RecyclerView.Adapter<ListViewHolder>{

        private List<CustomUsageStats> mList;

        public Adapter(List<CustomUsageStats> list) {
            mList = new ArrayList<>();
            this.mList.addAll(list);
            Collections.sort(mList, new Comparator<CustomUsageStats>() {
                @Override
                public int compare(CustomUsageStats o1, CustomUsageStats o2) {
                    return (int)(o2.lastTimeStamp-o1.lastTimeStamp);
                }
            });

        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_material_list_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            CustomUsageStats customUsageStats = getItem(position);
            holder.mImageView.setImageDrawable(customUsageStats.appIcon);
            holder.mTitleView.setText(customUsageStats.packageName);
            Log.d("Test", "time:"+customUsageStats.totalTime);
            holder.mSubTitleView.setText(mDateFormat.format(new Date(customUsageStats.lastTimeStamp)));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private CustomUsageStats getItem(int position){
            return mList.get(position);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_left_icon)
        ImageView mImageView;
        @BindView(R.id.list_title)
        TextView mTitleView;
        @BindView(R.id.list_subtitle)
        TextView mSubTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    public List<CustomUsageStats> update(List<UsageStats> list, UsageEvents events) {
        Map<String, CustomUsageStats> data = new HashMap<>();
        List<CustomUsageStats> customUsageStatsList = new ArrayList<>();
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
                Drawable appIcon = getActivity().getPackageManager()
                        .getApplicationIcon(usageStats.getPackageName());
                customUsageStats.appIcon = appIcon;
                if (customUsageStats.packageName.contains("com.google.android") ||
                        customUsageStats.packageName.contains("com.android.providers")
                        || customUsageStats.packageName.contains("com.android.")
                        || customUsageStats.packageName.contains("com.qualcomm.qti")
                        || customUsageStats.packageName.contains("com.quicinc.cne")
                        || customUsageStats.packageName.contains("org.codeaurora.ims")
                        || customUsageStats.packageName.equals("android")) {
                    continue;
                }
            } catch (Exception e){
                continue;
            }

            data.put(customUsageStats.packageName, customUsageStats);
            customUsageStatsList.add(customUsageStats);
        }

        UsageEvents.Event event = null;
        UsageEvents.Event perevent = null;

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
        return customUsageStatsList;
    }
}
