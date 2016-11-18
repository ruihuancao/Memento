package com.memento.android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.memento.android.R;
import com.memento.android.helper.DataHelper;
import com.memento.android.event.Event;
import com.memento.android.assistlibrary.util.ActivityUtils;
import com.memento.android.ui.base.BaseActivity;
import com.memento.android.ui.main.MainActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

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
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentLayout);
        }
        new LoginPresenter(DataHelper.getData(), loginFragment);
    }


    public static Intent getCallIntent(Context context) {
        return new Intent(context,LoginActivity.class);
    }

    @Subscribe
    public void onEvent(Event.LoginSuccessEvent event) {
        startActivity(MainActivity.getCallIntent(this));
        finish();
    }

    @Subscribe
    public void onEvent(Event.RegisterSuccessEvent event) {
        startActivity(MainActivity.getCallIntent(this));
        finish();
    }
}

