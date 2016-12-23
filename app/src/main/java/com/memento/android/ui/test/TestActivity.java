package com.memento.android.ui.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.memento.android.util.AppUtils;
import com.memento.android.R;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.widget.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private LinearLayoutManager mLinearLayoutManager;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new Adapter();
        mRecyclerview.setAdapter(mAdapter);
    }

    public static Intent getCallIntent(Context context){
        return new Intent(context, TestActivity.class);
    }



    class Adapter extends RecyclerView.Adapter<ListViewHolder>{


        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_common_list_item, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            holder.mImageView.setImageDrawable(AppUtils.getAppIcon(getApplication(), getPackageName()));

            switch (position){
                case 0:
                    holder.mTitleView.setText("获取应用名称");
                    holder.mSubTitleView.setText(AppUtils.getAppName(getApplication(), getPackageName()));
                    break;
                case 1:
                    holder.mTitleView.setText("获取应用图标");
                    holder.mImageView.setImageDrawable(AppUtils.getAppIcon(getApplication(), getPackageName()));
                    break;
                case 2:
                    holder.mTitleView.setText("获取应用更新日期");
                    holder.mSubTitleView.setText(String.valueOf(AppUtils.getAppDate(getApplication(), getPackageName())));
                    break;
                case 3:
                    holder.mTitleView.setText("获取应用大小");
                    holder.mSubTitleView.setText(String.valueOf(AppUtils.getAppSize(getApplication(), getPackageName())));
                    break;
                case 4:
                    holder.mTitleView.setText("获取应用apk文件");
                    holder.mSubTitleView.setText(AppUtils.getAppApk(getApplication(), getPackageName()));
                    break;
                case 5:
                    holder.mTitleView.setText("获取版本名称");
                    holder.mSubTitleView.setText(AppUtils.getAppVersionName(getApplication(), getPackageName()));
                    break;
                case 6:
                    holder.mTitleView.setText("获取版本号");
                    holder.mSubTitleView.setText(String.valueOf(AppUtils.getAppVersionCode(getApplication(), getPackageName())));
                    break;
                case 7:
                    holder.mTitleView.setText("获取应用的安装市场");
                    holder.mSubTitleView.setText(AppUtils.getAppInstaller(getApplication(), getPackageName()));
                    break;
                case 8:
                    holder.mTitleView.setText("获取应用包名");
                    holder.mSubTitleView.setText(AppUtils.getAppPackageName(getApplication()));
                    break;
                case 9:
                    holder.mTitleView.setText("应用是否安装");
                    holder.mSubTitleView.setText(String.valueOf(AppUtils.isInstalled(getApplication(), getPackageName())));
                    break;
                case 10:
                    holder.mTitleView.setText("是否是系统应用");
                    holder.mSubTitleView.setText(String.valueOf(AppUtils.isSystemApp(getApplication(), getPackageName())));
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 11;
        }
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        public ImageView mImageView;
        @BindView(R.id.list_title)
        public TextView mTitleView;
        @BindView(R.id.list_subtitle)
        public TextView mSubTitleView;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
