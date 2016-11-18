package com.memento.android.ui.login;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.assistlibrary.data.entity.LeanCloudUser;
import com.memento.android.assistlibrary.util.AppUtils;
import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    LoginContract.Presenter loginPresenter;

    Unbinder unbinder;
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    TextInputEditText mPasswordView;
    @BindView(R.id.email_sign_in_button)
    Button emailSignInButton;
    @BindView(R.id.email_register_button)
    Button emailRegisterButton;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.login_form)
    ScrollView loginForm;
    @BindView(R.id.username)
    TextInputEditText usernameView;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        populateAutoComplete();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                } else if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return false;
                }
                return false;
            }
        });

        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailLayout.setVisibility(View.GONE);
                attemptLogin();
            }
        });

        emailRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailLayout.setVisibility(View.VISIBLE);
                attemptRegister();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.subscribe();
    }


    private void populateAutoComplete() {
        List<String> emails = new ArrayList<>();
        emails.add("@163.com");
        emails.add("@dxy.com");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, emails);
        mEmailView.setAdapter(adapter);
    }

    private void attemptRegister() {
        AppUtils.hideSoftInput(getContext());
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameView.getText().toString().trim();
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(TextUtils.isEmpty(username)){
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            loginPresenter.register(username, password, email);
        }
    }

    private void attemptLogin() {
        AppUtils.hideSoftInput(getContext());
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameView.getText().toString().trim();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(TextUtils.isEmpty(username)){
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            loginPresenter.login(username, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void showError(String... message) {
        if (message.length > 0) {
            Snackbar.make(loginForm, message[0], Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(loginForm, "未知错误", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoginSuccess(LeanCloudUser leanCloudUser) {
        EventBus.getDefault().post(new Event.LoginSuccessEvent(leanCloudUser));
    }

    @Override
    public void showRegisterSuccess(LeanCloudUser leanCloudUser) {
        EventBus.getDefault().post(new Event.RegisterSuccessEvent(leanCloudUser));
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        loginPresenter.unsubscribe();
    }
}
