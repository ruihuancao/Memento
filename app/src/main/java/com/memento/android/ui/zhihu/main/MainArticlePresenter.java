package com.memento.android.ui.zhihu.main;


import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.data.Repository;
import com.memento.android.data.model.Address;
import com.memento.android.data.model.Article;
import com.memento.android.ui.base.BasePresenter;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-02-16
 * Time: 11:27
 *
 */
public class MainArticlePresenter extends BasePresenter<MainArticleContract.View> implements MainArticleContract.UserActionsListener {

    private final Repository mRepository;

    @Inject
    public MainArticlePresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }


    public void test(String ip){
        Logger.d("ip:"+ip);
        mRepository.getAddress(ip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Address>(){
                    @Override
                    public void onNext(Address address) {
                        super.onNext(address);
                        Logger.d(address.getArea() + ":" +address.getLocaltion());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Logger.d(e.getMessage());
                    }
                });
    }

    @Override
    public void getNewArticle(String... date) {
        if(date != null && date.length > 0){
            mRepository.getNewArticle(date[0])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<List<Article>>(){

                        @Override
                        public void onNext(List<Article> articles) {
                            super.onNext(articles);
                            getView().showList(articles);
                        }
                    });
        }else{
            mRepository.getNewArticle()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<List<Article>>(){

                        @Override
                        public void onNext(List<Article> articles) {
                            super.onNext(articles);
                            getView().showNewList(articles);
                        }
                    });
        }
    }

}