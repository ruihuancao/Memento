package com.memento.android.ui.zhihu.detail;


import com.memento.android.data.model.ArticleDetail;

/**
 * 作者: caorh@dxyer.com
 * 日期: 2016-04-13
 * 时间: 09:31
 * 描述：
 * 修改历史：
 */
public class ArticleDetailContract {

    interface View {
        void showArticle(ArticleDetail articleDetail);
        void showError();
    }

    interface UserActionsListener {
        void getArticleDetail(String id);
    }
}  