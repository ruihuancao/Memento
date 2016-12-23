package com.memento.android.ui.login;

import android.support.annotation.NonNull;

import com.memento.android.data.DataManager;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.data.subscriber.DefaultSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private CompositeSubscription mSubscriptions;

    private DataManager mDataManager;
    private LoginContract.View mView;

    public LoginPresenter(@NonNull DataManager dataManager, @NonNull LoginContract.View view) {
        mDataManager = checkNotNull(dataManager, "mDataSource cannot be null");
        mView = checkNotNull(view, "view cannot be null!");
        mSubscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void login(final String userName, final String passwd) {
        final LeanCloudUserBean user = new LeanCloudUserBean();
        user.setUsername(userName);
        user.setPassword(passwd);
        Subscription loginSubscription = mDataManager.getDataSource()
                .login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<LeanCloudUserBean>(){

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }

                    @Override
                    public void onNext(LeanCloudUserBean leanCloudUser) {
                        super.onNext(leanCloudUser);
                        if(leanCloudUser.getSessionToken() != null){
                            mView.showLoginSuccess(leanCloudUser);
                        }else{
                            if(leanCloudUser.getMessage() != null){
                                mView.showError(leanCloudUser.getMessage());
                            }else{
                                mView.showError();
                            }
                        }
                    }
                });
        mSubscriptions.add(loginSubscription);
    }
}

