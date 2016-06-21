package com.memento.android.ui.splash;

/**
 * Created by android on 16-5-27.
 */
public class SplashContract {

    interface View {
        void showImage(String url);
    }

    interface UserActionsListener {
        void getImage(int width);
    }
}
