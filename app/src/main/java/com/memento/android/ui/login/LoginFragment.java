package com.memento.android.ui.login;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.bean.LeanCloudUserBean;
import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.util.AppUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {


    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.email_sign_in_button)
    Button emailSignInButton;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.create_user)
    TextView createUser;
    @BindView(R.id.forget_passwd)
    TextView forgetPasswd;

    private LoginContract.Presenter loginPresenter;
    private LoginFragment.OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            mListener = (LoginFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        emailSignInButton.setOnClickListener(this);
        createUser.setOnClickListener(this);
        forgetPasswd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.create_user:
                mListener.register();
                break;
            case R.id.forget_passwd:
                mListener.forgetPasswd();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.subscribe();
    }

    private void attemptLogin() {
        AppUtils.hideSoftInput(getContext());
        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String nameStr = username.getText().toString().trim();
        String passwordStr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(passwordStr)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        } else if (!isPasswordValid(passwordStr)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(nameStr)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            loginPresenter.login(nameStr, passwordStr);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void showError(String... message) {
        if (message.length > 0) {
            Snackbar.make(emailLoginForm, message[0], Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(emailLoginForm, "未知错误", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoginSuccess(LeanCloudUserBean leanCloudUserBean) {
        EventBus.getDefault().post(new Event.LoginSuccessEvent(leanCloudUserBean));
        mListener.loginResult();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginPresenter.unsubscribe();
    }

    public interface OnFragmentInteractionListener {
        void register();
        void forgetPasswd();
        void loginResult();
    }
}
