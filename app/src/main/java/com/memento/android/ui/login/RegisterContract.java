package com.memento.android.ui.login;

import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;


public interface RegisterContract {

    interface View extends BaseView<RegisterContract.Presenter> {
        void showError(String ... message);
        void showRegisterSuccess(LeanCloudUserBean user);
    }

    interface Presenter extends BasePresenter {
        void register(String user, String passwd, String email);
    }
}
