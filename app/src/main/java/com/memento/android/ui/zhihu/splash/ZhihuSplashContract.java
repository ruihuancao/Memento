package com.memento.android.ui.zhihu.splash;

import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface ZhihuSplashContract {

    interface View extends BaseView<Presenter> {
        void showImage(String url);
        void showError();
    }

    interface Presenter extends BasePresenter {

        void loadImageUrl();
    }
}
