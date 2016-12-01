package com.memento.android.ui.login;

import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showError(String ... message);
        void showRegisterSuccess(LeanCloudUserEntiry user);
        void showLoginSuccess(LeanCloudUserEntiry user);
    }

    interface Presenter extends BasePresenter {
        void login(String user, String passwd);
        void register(String user, String passwd, String email);

    }
}
