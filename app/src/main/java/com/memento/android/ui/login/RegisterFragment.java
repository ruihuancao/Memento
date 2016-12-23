package com.memento.android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.TextView;

import com.memento.android.R;
import com.memento.android.data.source.entity.LeanCloudUserEntiry;
import com.memento.android.event.Event;
import com.memento.android.ui.base.BaseFragment;
import com.memento.android.util.AppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by android on 16-12-6.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View{

    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.email_register_button)
    Button emailRegisterButton;
    @BindView(R.id.email_register_form)
    LinearLayout emailRegisterForm;

    private RegisterFragment.OnFragmentInteractionListener mListener;

    private RegisterContract.Presenter mPresenter;


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.OnFragmentInteractionListener) {
            mListener = (RegisterFragment.OnFragmentInteractionListener) context;
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        populateAutoComplete();
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return false;
                }
                return false;
            }
        });

        emailRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        return view;
    }

    private void populateAutoComplete() {
        List<String> emails = new ArrayList<>();
        emails.add("@163.com");
        emails.add("@dxy.com");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, emails);
        email.setAdapter(adapter);
    }


    private void attemptRegister() {
        AppUtils.hideSoftInput(getContext());
        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String nameStr = username.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(emailStr)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailStr)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

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
            //loginPresenter.register(username, password, email);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void showRegisterSuccess(LeanCloudUserEntiry leanCloudUserEntiry) {
        EventBus.getDefault().post(new Event.RegisterSuccessEvent(leanCloudUserEntiry));
        mListener.registerResult();
    }

    @Override
    public void showError(String... message) {
        if (message.length > 0) {
            Snackbar.make(emailRegisterForm, message[0], Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(emailRegisterForm, "未知错误", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public interface OnFragmentInteractionListener {
        void registerResult();
    }

}
