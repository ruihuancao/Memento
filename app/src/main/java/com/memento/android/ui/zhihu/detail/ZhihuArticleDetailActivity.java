package com.memento.android.ui.zhihu.detail;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.memento.android.R;
import com.memento.android.data.DataManager;
import com.memento.android.data.entity.ZhihuArticleDetailEmtity;
import com.memento.android.subscriber.DefaultSubscriber;
import com.memento.android.model.mapper.DataMapper;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.webview.CustomTabActivityHelper;
import com.memento.android.ui.webview.WebviewFallback;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ZhihuArticleDetailActivity extends BaseActivity{

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

    @Inject
    DataManager mDataManager;
    @Inject
    DataMapper mDataMapper;

    private Subscription mSubscription;

    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_zhihu_article_detail);
        ButterKnife.bind(this);
        setupWindowAnimations();
        initExtra();
        initView();
        initData(articleId);
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
            case R.id.action_settings:
                // your logic
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

    private void initData(String id){
        if(TextUtils.isEmpty(id)){
            return;
        }
        mSubscription = mDataManager.getArticleDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuArticleDetailEmtity, ZhihuArticleDetailEmtity>() {
                    @Override
                    public ZhihuArticleDetailEmtity call(ZhihuArticleDetailEmtity articleDetail) {
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
                        StringBuilder stringBuilder = new StringBuilder();
                        if(articleDetail.getCss() != null){
                            for (String css : articleDetail.getCss()){
                                stringBuilder.append(String.format(getString(R.string.zhihu_css), css));
                            }
                        }

                        if(content != null){
                            content = String.format(content, stringBuilder.toString() ,articleDetail.getBody());
                        }
                        articleDetail.setBody(content);
                        return articleDetail;
                    }
                })
                .subscribe(new DefaultSubscriber<ZhihuArticleDetailEmtity>(){

                    @Override
                    public void onNext(ZhihuArticleDetailEmtity articleDetail) {
                        super.onNext(articleDetail);
                        mCollapsingToolbar.setTitle(articleDetail.getTitle());
                        Glide.with(ZhihuArticleDetailActivity.this).load(articleDetail.getImage()).into(mBackdrop);
                        mWebview.loadDataWithBaseURL(null, articleDetail.getBody(), "text/html", "UTF-8", null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription != null && mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
