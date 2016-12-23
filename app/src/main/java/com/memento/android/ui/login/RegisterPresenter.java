package com.memento.android.ui.login;

import android.support.annotation.NonNull;

import com.memento.android.data.DataManager;
import com.memento.android.data.source.entity.LeanCloudUserEntiry;
import com.memento.android.contants.ErrorCode;
import com.memento.android.helper.LoginHelper;
import com.memento.android.data.subscriber.DefaultSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by android on 16-12-6.
 */

public class RegisterPresenter implements RegisterContract.Presenter {


    private CompositeSubscription mSubscriptions;

    private DataManager mDataManager;
    private RegisterContract.View mView;

    public RegisterPresenter(@NonNull DataManager dataManager, @NonNull RegisterContract.View view) {
        mDataManager = checkNotNull(dataManager, "mDataSource cannot be null");
        mView = checkNotNull(view, "view cannot be null!");
        mSubscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }


    @Override
    public void register(final String userName, final String passwd, final String email) {
        final LeanCloudUserEntiry user = new LeanCloudUserEntiry();
        user.setUsername(userName);
        user.setPassword(passwd);
        user.setEmail(email);
        user.setAvatar(LoginHelper.getDefaultAvatar(email));
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
                            mView.showError(leanCloudUser.getMessage());
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
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
