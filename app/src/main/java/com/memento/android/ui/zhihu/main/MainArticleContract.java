package com.memento.android.ui.zhihu.main;


import com.memento.android.data.model.Article;

import java.util.List;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-04-01
 * Time: 17:01
 *
 */
public interface MainArticleContract {

    interface View {
        void showList(List<Article> mList);
        void showNewList(List<Article> mList);
    }

    interface UserActionsListener {
        void getNewArticle(String... date);
    }
}  