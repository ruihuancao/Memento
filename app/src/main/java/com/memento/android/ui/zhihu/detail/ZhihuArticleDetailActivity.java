package com.memento.android.ui.zhihu.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.data.model.ArticleDetail;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.webview.CustomTabActivityHelper;
import com.memento.android.ui.webview.WebviewFallback;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZhihuArticleDetailActivity extends BaseActivity implements ArticleDetailContract.View {

    private static final String TAG = ZhihuArticleDetailActivity.class.getSimpleName();

    @Bind(R.id.backdrop)
    ImageView mBackdrop;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;
    @Bind(R.id.webview)
    WebView mWebview;

    @Inject
    ArticleDetailPresenter mArticleDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu_article_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                CustomTabActivityHelper.openCustomTab(
                        ZhihuArticleDetailActivity.this, customTabsIntent, Uri.parse(url), new WebviewFallback());
                return true;
            }
        });

        mArticleDetailPresenter.attachView(this);
        String id = getIntent().getStringExtra(PARAM_ONE);
        if (!TextUtils.isEmpty(id)) {
            mArticleDetailPresenter.getArticleDetail(id);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static Intent getCallingIntent(Context mContext, String id) {
        Intent intent = new Intent(mContext, ZhihuArticleDetailActivity.class);
        intent.putExtra(PARAM_ONE, id);
        return intent;
    }

    @Override
    public void showArticle(ArticleDetail articleDetail) {
        mCollapsingToolbar.setTitle(articleDetail.getTitle());
        Glide.with(this).load(articleDetail.getImageUrl()).into(mBackdrop);
        mWebview.loadDataWithBaseURL(null, articleDetail.getContent(), "text/html", "UTF-8", null);
    }

    @Override
    public void showError() {

    }
}
