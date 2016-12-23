package com.memento.android.ui.zhihu.main;

import android.support.annotation.NonNull;

import com.memento.android.bean.ArticleBannerBean;
import com.memento.android.bean.ArticleBean;
import com.memento.android.data.DataManager;
import com.memento.android.bean.ZhihuNewsBean;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public class ZhihuPresenter implements ZhihuContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private DataManager mDataManager;
    private ZhihuContract.View mView;

    public ZhihuPresenter(@NonNull DataManager dataManager, @NonNull ZhihuContract.View view) {
        mDataManager = checkNotNull(dataManager, "dataManager cannot be null");
        mView =  checkNotNull(view, "splashview cannot be null!");
        mSubscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getNewArticle();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getNewArticle() {
        Subscription subscribe = mDataManager.getDataSource()
                .getNewArticleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuNewsBean, List<ArticleBean>>() {
                    @Override
                    public List<ArticleBean> call(ZhihuNewsBean zhihuArticleEntity) {
                        return transform(zhihuArticleEntity);
                    }
                })
                .subscribe(new DefaultSubscriber<List<ArticleBean>>(){

                    @Override
                    public void onNext(List<ArticleBean> articleModels) {
                        super.onNext(articleModels);
                        mView.showNewList(articleModels);
                    }
                });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void getNextArticle(String date) {
        Subscription subscribe = mDataManager.getDataSource()
                .getArticleList(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuNewsBean, List<ArticleBean>>() {
                    @Override
                    public List<ArticleBean> call(ZhihuNewsBean zhihuArticleEntity) {
                        return transform(zhihuArticleEntity);
                    }
                })
                .subscribe(new DefaultSubscriber<List<ArticleBean>>(){

                    @Override
                    public void onNext(List<ArticleBean> articleModels) {
                        super.onNext(articleModels);
                        mView.showNextList(articleModels);
                    }
                });
        mSubscriptions.add(subscribe);
    }


    public List<ArticleBean> transform(ZhihuNewsBean zhihuArticleEntity){
        List<ArticleBean> mList = null;
        boolean isToday = false;
        if(zhihuArticleEntity != null && zhihuArticleEntity.getTop_stories() != null
                && zhihuArticleEntity.getTop_stories().size() > 0){
            isToday = true;
            if(mList == null){
                mList = new ArrayList<>();
            }
            ArticleBean articleBean = new ArticleBean();
            articleBean.setType(0);
            articleBean.setDate(zhihuArticleEntity.getDate());
            List<ArticleBannerBean> mBanner = new ArrayList<>();
            ArticleBannerBean articleBannerBean = null;
            for (ZhihuNewsBean.TopStoriesEntity topStoriesEntity : zhihuArticleEntity.getTop_stories()){
                articleBannerBean = new ArticleBannerBean();
                articleBannerBean.setUrl(topStoriesEntity.getImage());
                articleBannerBean.setTitle(topStoriesEntity.getTitle());
                articleBannerBean.setId(String.valueOf(topStoriesEntity.getId()));
                mBanner.add(articleBannerBean);
            }
            articleBean.setArticleBannerModels(mBanner);
            mList.add(articleBean);
        }
        if(zhihuArticleEntity != null && zhihuArticleEntity.getStories() != null
                && zhihuArticleEntity.getStories().size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }

            ArticleBean titleArticleBean = new ArticleBean();
            titleArticleBean.setType(1);
            titleArticleBean.setDate(zhihuArticleEntity.getDate());
            titleArticleBean.setTitle(TimeUtil.converDate(zhihuArticleEntity.getDate()));
            mList.add(titleArticleBean);
            if(isToday){
                titleArticleBean.setTitle("今日新闻");
            }

            ArticleBean articleBean = null;
            for (ZhihuNewsBean.StoriesEntity storiesEntity : zhihuArticleEntity.getStories()){
                articleBean = new ArticleBean();
                if(storiesEntity.getImages() != null && storiesEntity.getImages().size() > 0){
                    articleBean.setImageUrl(storiesEntity.getImages().get(0));
                }
                articleBean.setTitle(storiesEntity.getTitle());
                articleBean.setId(String.valueOf(storiesEntity.getId()));
                articleBean.setDate(zhihuArticleEntity.getDate());
                articleBean.setType(2);
                mList.add(articleBean);
            }
        }
        return mList;
    }
}
