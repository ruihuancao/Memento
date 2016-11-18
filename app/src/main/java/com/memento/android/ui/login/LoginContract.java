package com.memento.android.ui.login;

import com.memento.android.assistlibrary.data.entity.LeanCloudUser;
import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showError(String ... message);
        void showRegisterSuccess(LeanCloudUser user);
        void showLoginSuccess(LeanCloudUser user);
    }

    interface Presenter extends BasePresenter {
        void login(String user, String passwd);
        void register(String user, String passwd, String email);

    }
}
