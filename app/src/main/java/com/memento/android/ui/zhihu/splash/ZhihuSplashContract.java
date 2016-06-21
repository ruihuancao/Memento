package com.memento.android.ui.zhihu.splash;

/**
 * User: caoruihuan(cao_ruihuan@163.com)
 * Date: 2016-03-11
 * Time: 16:14
 *
 */
public interface ZhihuSplashContract {

    interface View {
        void showImage(String imageUrl);

        void showError();
    }

    interface UserActionsListener {
        void getImage(String deveceInfo);
    }
}  