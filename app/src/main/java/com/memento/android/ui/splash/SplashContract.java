package com.memento.android.ui.splash;

import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void showImage(String url);
        void showError();
    }

    interface Presenter extends BasePresenter {

        void loadImageUrl();
    }
}
