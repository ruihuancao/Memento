package com.memento.android.ui.zhihu.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.memento.android.R;
import com.memento.android.data.model.Article;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.zhihu.detail.ZhihuArticleDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZhihuActivity extends BaseActivity
        implements ZhihuMainFragment.OnFragmentInteractionListener {

    private static final String TAG = ZhihuActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }



    @Override
    public void onClickListItem(Article article) {
        if (article != null) {
            startActivity(ZhihuArticleDetailActivity.getCallingIntent(this, article.getId()));
        }
    }

    public static Intent getCallingIntent(Context mContext) {
        return new Intent(mContext, ZhihuActivity.class);
    }
}
