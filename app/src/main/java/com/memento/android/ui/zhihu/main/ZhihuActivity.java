package com.memento.android.ui.zhihu.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.memento.android.R;
import com.memento.android.model.ArticleModel;
import com.memento.android.navigation.Navigator;
import com.memento.android.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhihuActivity extends BaseActivity
        implements ZhihuMainFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //setupWindowAnimations();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.BOTTOM);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    @Override
    public void onClickListItem(ArticleModel articleModel, Pair<View, String>[] pairs) {
        if (articleModel != null) {
            mNavigator.openZhihuDetailActivity(this, articleModel.getId(), pairs);
        }
    }
}
