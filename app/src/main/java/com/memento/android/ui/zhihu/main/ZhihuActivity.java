package com.memento.android.ui.zhihu.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;

import com.crh.android.common.util.ActivityUtils;
import com.memento.android.R;
import com.memento.android.event.Event;
import com.memento.android.helper.DataHelper;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.zhihu.detail.ZhihuArticleDetailActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ZhihuMainFragment zhihuMainFragment =
                (ZhihuMainFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (zhihuMainFragment == null) {
            zhihuMainFragment = ZhihuMainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), zhihuMainFragment, R.id.contentLayout);
        }
        new ZhihuPresenter(DataHelper.provideDataManager(getApplicationContext()), zhihuMainFragment);
    }

    public static Intent getCallIntent(Context context){
        return new Intent(context, ZhihuActivity.class);
    }


    @Subscribe
    public void onEvent(Event.OpenZhihuDetailActivity event) {
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, event.pairs);
        startActivity(ZhihuArticleDetailActivity.getCallIntent(this, event.id), transitionActivityOptions.toBundle());
    }
}
