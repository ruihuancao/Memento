package com.memento.android.ui.zhihu.main;

import android.support.annotation.NonNull;

import com.memento.android.data.DataManager;
import com.memento.android.data.entity.ZhihuArticleEntity;
import com.memento.android.model.ArticleModel;
import com.memento.android.model.mapper.DataMapper;
import com.memento.android.subscriber.DefaultSubscriber;

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

    private DataMapper mDataMapper;

    public ZhihuPresenter(@NonNull DataManager dataManager, DataMapper dataMapper, @NonNull ZhihuContract.View view) {
        mDataManager = checkNotNull(dataManager, "dataManager cannot be null");
        mView =  checkNotNull(view, "splashview cannot be null!");
        mDataMapper =  dataMapper;
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
        Subscription subscribe = mDataManager.getNewArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuArticleEntity, List<ArticleModel>>() {
                    @Override
                    public List<ArticleModel> call(ZhihuArticleEntity zhihuArticleEntity) {
                        return mDataMapper.transform(zhihuArticleEntity);
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
        Subscription subscribe = mDataManager.getNewArticle(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuArticleEntity, List<ArticleModel>>() {
                    @Override
                    public List<ArticleModel> call(ZhihuArticleEntity zhihuArticleEntity) {
                        return mDataMapper.transform(zhihuArticleEntity);
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
}
