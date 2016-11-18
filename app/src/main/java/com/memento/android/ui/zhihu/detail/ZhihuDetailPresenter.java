package com.memento.android.ui.zhihu.detail;

import android.support.annotation.NonNull;

import com.memento.android.assistlibrary.data.DataManager;
import com.memento.android.assistlibrary.data.entity.ZhihuDetailEntity;
import com.memento.android.assistlibrary.data.subscriber.DefaultSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public class ZhihuDetailPresenter implements ZhihuDetailContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private DataManager mDataManager;
    private ZhihuDetailContract.View mView;
    private String id;
    private String contentTemplate;
    private String cssTemplate;

    public ZhihuDetailPresenter(@NonNull DataManager dataManager, @NonNull ZhihuDetailContract.View view, String id, String contentTemplate,
                                String cssTemplate) {
        mDataManager = checkNotNull(dataManager, "dataManager cannot be null");
        mView =  checkNotNull(view, "splashview cannot be null!");
        this.id =  id;
        this.contentTemplate = contentTemplate;
        this.cssTemplate = cssTemplate;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        loadDetail(id);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadDetail(String id) {
        Subscription subscribe = mDataManager.getZhihuApiService().getArticleDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuDetailEntity, ZhihuDetailEntity>() {
                    @Override
                    public ZhihuDetailEntity call(ZhihuDetailEntity articleDetail) {
                        StringBuilder stringBuilder = new StringBuilder();
                        if(articleDetail.getCss() != null){
                            for (String css : articleDetail.getCss()){
                                stringBuilder.append(String.format(cssTemplate, css));
                            }
                        }
                        articleDetail.setBody(String.format(contentTemplate, stringBuilder.toString() ,articleDetail.getBody()));
                        return articleDetail;
                    }
                })
                .subscribe(new DefaultSubscriber<ZhihuDetailEntity>(){

                    @Override
                    public void onNext(ZhihuDetailEntity articleDetail) {
                        super.onNext(articleDetail);
                        mView.showDetail(articleDetail.getTitle(), articleDetail.getImage(), articleDetail.getBody());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }
                });
        mSubscriptions.add(subscribe);
    }
}
