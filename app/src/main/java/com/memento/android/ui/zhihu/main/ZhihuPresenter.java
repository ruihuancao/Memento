package com.memento.android.ui.zhihu.main;

import android.support.annotation.NonNull;

import com.crh.android.common.data.DataRepository;
import com.crh.android.common.data.source.entity.ZhihuNewsEntity;
import com.crh.android.common.subscriber.DefaultSubscriber;
import com.crh.android.common.util.TimeUtil;
import com.memento.android.bean.ArticleBannerModel;
import com.memento.android.bean.ArticleModel;

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

    private DataRepository mDataRepository;
    private ZhihuContract.View mView;

    public ZhihuPresenter(@NonNull DataRepository dataRepository, @NonNull ZhihuContract.View view) {
        mDataRepository = checkNotNull(dataRepository, "dataRepository cannot be null");
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
        Subscription subscribe = mDataRepository
                .getNewArticleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuNewsEntity, List<ArticleModel>>() {
                    @Override
                    public List<ArticleModel> call(ZhihuNewsEntity zhihuArticleEntity) {
                        return transform(zhihuArticleEntity);
                    }
                })
                .subscribe(new DefaultSubscriber<List<ArticleModel>>(){

                    @Override
                    public void onNext(List<ArticleModel> articleModels) {
                        super.onNext(articleModels);
                        mView.showNewList(articleModels);
                    }
                });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void getNextArticle(String date) {
        Subscription subscribe = mDataRepository.getArticleList(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuNewsEntity, List<ArticleModel>>() {
                    @Override
                    public List<ArticleModel> call(ZhihuNewsEntity zhihuArticleEntity) {
                        return transform(zhihuArticleEntity);
                    }
                })
                .subscribe(new DefaultSubscriber<List<ArticleModel>>(){

                    @Override
                    public void onNext(List<ArticleModel> articleModels) {
                        super.onNext(articleModels);
                        mView.showNextList(articleModels);
                    }
                });
        mSubscriptions.add(subscribe);
    }


    public List<ArticleModel> transform(ZhihuNewsEntity zhihuArticleEntity){
        List<ArticleModel> mList = null;
        boolean isToday = false;
        if(zhihuArticleEntity != null && zhihuArticleEntity.getTop_stories() != null
                && zhihuArticleEntity.getTop_stories().size() > 0){
            isToday = true;
            if(mList == null){
                mList = new ArrayList<>();
            }
            ArticleModel articleModel = new ArticleModel();
            articleModel.setType(0);
            articleModel.setDate(zhihuArticleEntity.getDate());
            List<ArticleBannerModel> mBanner = new ArrayList<>();
            ArticleBannerModel articleBannerModel = null;
            for (ZhihuNewsEntity.TopStoriesEntity topStoriesEntity : zhihuArticleEntity.getTop_stories()){
                articleBannerModel = new ArticleBannerModel();
                articleBannerModel.setUrl(topStoriesEntity.getImage());
                articleBannerModel.setTitle(topStoriesEntity.getTitle());
                articleBannerModel.setId(String.valueOf(topStoriesEntity.getId()));
                mBanner.add(articleBannerModel);
            }
            articleModel.setArticleBannerModels(mBanner);
            mList.add(articleModel);
        }
        if(zhihuArticleEntity != null && zhihuArticleEntity.getStories() != null
                && zhihuArticleEntity.getStories().size() > 0){
            if(mList == null){
                mList = new ArrayList<>();
            }

            ArticleModel titleArticleModel = new ArticleModel();
            titleArticleModel.setType(1);
            titleArticleModel.setDate(zhihuArticleEntity.getDate());
            titleArticleModel.setTitle(TimeUtil.converDate(zhihuArticleEntity.getDate()));
            mList.add(titleArticleModel);
            if(isToday){
                titleArticleModel.setTitle("今日新闻");
            }

            ArticleModel articleModel = null;
            for (ZhihuNewsEntity.StoriesEntity storiesEntity : zhihuArticleEntity.getStories()){
                articleModel = new ArticleModel();
                if(storiesEntity.getImages() != null && storiesEntity.getImages().size() > 0){
                    articleModel.setImageUrl(storiesEntity.getImages().get(0));
                }
                articleModel.setTitle(storiesEntity.getTitle());
                articleModel.setId(String.valueOf(storiesEntity.getId()));
                articleModel.setDate(zhihuArticleEntity.getDate());
                articleModel.setType(2);
                mList.add(articleModel);
            }
        }
        return mList;
    }
}
