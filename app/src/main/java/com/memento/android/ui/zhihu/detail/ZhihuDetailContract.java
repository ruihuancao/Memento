package com.memento.android.ui.zhihu.detail;

import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface ZhihuDetailContract {

    interface View extends BaseView<Presenter> {
        void showDetail(String title, String imageUrl, String content);
        void showError();
    }

    interface Presenter extends BasePresenter {
        void loadDetail(String id);
    }
}
