package com.memento.android.ui.login;

import android.support.annotation.NonNull;

import com.crh.android.common.data.DataManager;
import com.crh.android.common.data.source.entity.LeanCloudUserEntiry;
import com.crh.android.common.login.ErrorCode;
import com.crh.android.common.login.LoginManager;
import com.crh.android.common.subscriber.DefaultSubscriber;

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
    public void register(final String userName, final String passwd, final String email) {
        final LeanCloudUserEntiry user = new LeanCloudUserEntiry();
        user.setUsername(userName);
        user.setPassword(passwd);
        user.setEmail(email);
        user.setAvatar(LoginManager.getDefaultAvatar(email));
        Subscription registerSubscription = mDataManager.getDataSource()
                .register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<LeanCloudUserEntiry>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }

                    @Override
                    public void onNext(LeanCloudUserEntiry leanCloudUser) {
                        super.onNext(leanCloudUser);
                        if(leanCloudUser.getSessionToken() != null){
                            mView.showRegisterSuccess(leanCloudUser);
                        }else if (leanCloudUser.getStatusCode() == ErrorCode.CODE_202
                                || leanCloudUser.getStatusCode() == ErrorCode.CODE_203) {
                            // 用户已存在
                            login(userName, passwd);
                            return;
                        }else{
                            if(user.getMessage() != null){
                                mView.showError(user.getMessage());
                            }else{
                                mView.showError();
                            }
                        }
                    }
                });
        mSubscriptions.add(registerSubscription);
    }

    @Override
    public void login(final String userName, final String passwd) {
        final LeanCloudUserEntiry user = new LeanCloudUserEntiry();
        user.setUsername(userName);
        user.setPassword(passwd);
        Subscription loginSubscription = mDataManager.getDataSource()
                .login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<LeanCloudUserEntiry>(){

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }

                    @Override
                    public void onNext(LeanCloudUserEntiry leanCloudUser) {
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

