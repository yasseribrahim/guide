package com.azhar.university.guide.presentation.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.views.ParseView;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenter;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ParseView {
    @BindView(R.id.login_form)
    View loginFormView;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.password)
    EditText password;

    private ParsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        presenter = new ParsePresenterImp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected View getSnackBarAnchorView() {
        return loginFormView;
    }

    @OnEditorAction(R.id.password)
    public boolean onPasswordEditorAction(int id) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            login();
            return true;
        }
        return false;
    }

    @OnClick(R.id.join_now_button)
    public void onJoinNowClicked() {
        joinNow();
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInClicked() {
        login();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void login() {
        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            this.email.setError(getString(R.string.error_invalid_email));
            focusView = this.email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            presenter.login(email, password);
        }
    }

    private void joinNow() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onRegisterComplete() {

    }

    @Override
    public void onLoginComplete() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onLogoutComplete() {

    }

    @Override
    public void onEditProfileComplete() {

    }

    @Override
    public void showConnectionError(View.OnClickListener onClickListener) {
        showConnectionSnackBar(onClickListener);
    }

    @Override
    public void showError(String message, View.OnClickListener onClickListener) {
        showRetrySnackBar(message, onClickListener);
    }

    @Override
    public void unAuthorized() {

    }
}

