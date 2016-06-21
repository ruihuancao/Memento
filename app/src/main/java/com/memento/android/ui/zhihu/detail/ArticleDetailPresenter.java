package com.memento.android.ui.zhihu.detail;

import android.content.Context;

import com.memento.android.R;
import com.memento.android.data.Repository;
import com.memento.android.data.model.ArticleDetail;
import com.memento.android.data.subscriber.DefaultSubscriber;
import com.memento.android.injection.ActivityContext;
import com.memento.android.ui.base.BasePresenter;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-04-13
 * 时间: 09:32
 * 描述：
 * 修改历史：
 */
public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.UserActionsListener {

    private final Context mContext;
    private final Repository mRepository;


    @Inject
    public ArticleDetailPresenter(@ActivityContext Context context, Repository mRepository){
        this.mContext = context;
        this.mRepository = mRepository;
    }


    @Override
    public void getArticleDetail(String id) {
       mRepository.getArticleDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ArticleDetail, ArticleDetail>() {
                   @Override
                   public ArticleDetail call(ArticleDetail articleDetail) {
                       String content = null;
                       try{
                           InputStream inputStream = mContext.getAssets().open("template.html");
                           BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                           int available = bufferedInputStream.available();
                           byte[] contentByte = new byte[available];
                           bufferedInputStream.read(contentByte);
                           bufferedInputStream.close();
                           inputStream.close();
                           content = new String(contentByte);
                       }catch (Exception e){
                       }

                       StringBuilder stringBuilder = new StringBuilder();

                       if(articleDetail.getCss() != null){
                           for (String css : articleDetail.getCss()){
                               stringBuilder.append(String.format(mContext.getString(R.string.zhihu_css), css));
                           }
                       }

                       if(content != null){
                           content = String.format(content, stringBuilder.toString() ,articleDetail.getContent());
                       }
                       articleDetail.setContent(content);
                       return articleDetail;
                   }
               })
                .subscribe(new DefaultSubscriber<ArticleDetail>(){

                    @Override
                    public void onNext(ArticleDetail articleDetail) {
                        super.onNext(articleDetail);
                        if (articleDetail != null) {
                            getView().showArticle(articleDetail);
                        } else {
                            getView().showError();
                        }
                    }
                });
    }
}