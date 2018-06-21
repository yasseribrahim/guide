package com.azhar.university.guide.presentation.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.views.ParseView;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenter;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends BaseActivity implements ParseView {
    @BindView(R.id.login_form)
    View loginFormView;
    @BindView(R.id.full_name)
    AutoCompleteTextView name;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirmation_password)
    EditText confirmationPassword;
    @BindView(R.id.login_progress)
    View view;

    private ParsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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

    @OnClick(R.id.join_now_button)
    public void onJoinNowClicked() {
        joinNow();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void joinNow() {
        reset();

        // Store values at the time of the login attempt.
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String confirmationPassword = this.confirmationPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid full name.
        if (TextUtils.isEmpty(name)) {
            this.name.setError(getString(R.string.error_field_required));
            focusView = this.name;
            cancel = true;
        } else if (!isNameValid(name)) {
            this.name.setError(getString(R.string.error_invalid_full_name));
            focusView = this.name;
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

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            cancel = true;
        }

        // Check for a valid confiramtion password, if the user entered one.
        // Check for a valid email address.
        if (TextUtils.isEmpty(confirmationPassword)) {
            this.confirmationPassword.setError(getString(R.string.error_field_required));
            focusView = this.confirmationPassword;
            cancel = true;
        } else if (!isConfirmationPasswordValid(password, confirmationPassword)) {
            this.confirmationPassword.setError(getString(R.string.error_invalid_confirmation_password));
            focusView = this.confirmationPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            presenter.register(email, password, name);
        }
    }

    private void reset() {
        // Reset errors.
        email.setError(null);
        password.setError(null);
        name.setError(null);
        confirmationPassword.setError(null);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isConfirmationPasswordValid(String password, String confirmationPassword) {
        //TODO: Replace this with your own logic
        return password.equals(confirmationPassword);
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            view.setVisibility(show ? View.VISIBLE : View.GONE);
            view.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            view.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onRegisterComplete() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onLoginComplete() {
    }

    @Override
    public void onLogoutComplete() {

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
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    @Override
    public void unAuthorized() {

    }
}

