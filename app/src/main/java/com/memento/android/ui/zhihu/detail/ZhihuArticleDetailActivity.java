package com.memento.android.ui.zhihu.detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.crh.android.common.view.glide.GlideHelper;
import com.memento.android.R;
import com.memento.android.helper.DataHelper;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.webview.CustomTabActivityHelper;
import com.memento.android.ui.webview.WebviewFallback;

import java.io.BufferedInputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

;

public class ZhihuArticleDetailActivity extends BaseActivity implements ZhihuDetailContract.View{

    private static final String TAG = ZhihuArticleDetailActivity.class.getSimpleName();

    public static final String PARAM_ONE = "param_one";

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;
    @BindView(R.id.webview)
    WebView mWebview;


    private ZhihuDetailPresenter mPresenter;
    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_article_detail);
        ButterKnife.bind(this);
        setupWindowAnimations();
        initExtra();
        initView();
        mPresenter = new ZhihuDetailPresenter(DataHelper.provideRepository(getApplicationContext()), this, articleId, getContentTemplate(), getString(R.string.zhihu_css));
    }

    private String getContentTemplate(){
        String content = null;
        try{
            InputStream inputStream = getAssets().open("template.html");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int available = bufferedInputStream.available();
            byte[] contentByte = new byte[available];
            bufferedInputStream.read(contentByte);
            bufferedInputStream.close();
            inputStream.close();
            content = new String(contentByte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public static Intent getCallIntent(Context context, String id){
        Intent intent = new Intent(context, ZhihuArticleDetailActivity.class);
        intent.putExtra(ZhihuArticleDetailActivity.PARAM_ONE, id);
        return intent;
    }

    private void initExtra(){
        if(getIntent() != null){
            articleId = getIntent().getStringExtra(PARAM_ONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().getEnterTransition().setDuration(1000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                // your logic
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "send message"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initView(){
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                CustomTabActivityHelper.openCustomTab(
                        ZhihuArticleDetailActivity.this, customTabsIntent, Uri.parse(url), new WebviewFallback());
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void showDetail(String title, String imageUrl, String content) {
        mCollapsingToolbar.setTitle(title);
        GlideHelper.loadResource(imageUrl, mBackdrop);
        mWebview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(ZhihuDetailContract.Presenter presenter) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
