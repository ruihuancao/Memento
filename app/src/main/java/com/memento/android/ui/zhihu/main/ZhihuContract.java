package com.memento.android.ui.zhihu.main;

import com.memento.android.model.ArticleModel;
import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

import java.util.List;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface ZhihuContract {

    interface View extends BaseView<Presenter> {
        void showNewList(List<ArticleModel> articleModelList);
        void showNextList(List<ArticleModel> articleModelList);
    }

    interface Presenter extends BasePresenter {

        void getNewArticle();

        void getNextArticle(String date);
    }
}
