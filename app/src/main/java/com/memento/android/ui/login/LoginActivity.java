package com.memento.android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.memento.android.R;
import com.memento.android.helper.DataHelper;
import com.memento.android.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginActivity extends BaseActivity
        implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LoginFragment loginFragment = LoginFragment.newInstance();
        addFragmentToActivity(
                getSupportFragmentManager(), loginFragment, R.id.contentLayout);
        new LoginPresenter(DataHelper.provideDataManager(getApplicationContext()), loginFragment);
    }


    public static Intent getCallIntent(Context context) {
        return new Intent(context,LoginActivity.class);
    }

    @Override
    public void register() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        replaceFragmentToActivity(
                getSupportFragmentManager(), registerFragment, R.id.contentLayout);
        new RegisterPresenter(DataHelper.provideDataManager(getApplicationContext()), registerFragment);
    }

    @Override
    public void forgetPasswd() {

    }

    @Override
    public void loginResult() {

    }

    @Override
    public void registerResult() {

    }


    public void replaceFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}

