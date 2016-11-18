package com.memento.android.ui.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.memento.android.R;
import com.memento.android.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonActivity extends BaseActivity {

    @BindView(R.id.user_icon)
    ImageView userIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        
    }


    public static Intent getCallIntent(Context context) {
        return new Intent(context, PersonActivity.class);
    }

}
