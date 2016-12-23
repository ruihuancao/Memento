package com.memento.android.ui.login;

import com.memento.android.data.source.entity.LeanCloudUserEntiry;
import com.memento.android.ui.base.BasePresenter;
import com.memento.android.ui.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showError(String ... message);
        void showLoginSuccess(LeanCloudUserEntiry user);
    }

    interface Presenter extends BasePresenter {
        void login(String user, String passwd);
    }
}
