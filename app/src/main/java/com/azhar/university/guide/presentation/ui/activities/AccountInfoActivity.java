package com.azhar.university.guide.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.models.parse.User;
import com.azhar.university.guide.domain.utils.ParseManager;
import com.azhar.university.guide.domain.views.ParseView;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenter;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class AccountInfoActivity extends BaseActivity implements ParseView {
    @BindView(R.id.account_info_form)
    View accountInfoFormView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.full_name)
    AutoCompleteTextView name;
    @BindView(R.id.email)
    AutoCompleteTextView email;

    private ParsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        ButterKnife.bind(this);

        presenter = new ParsePresenterImp(this);

        setupUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupUI() {
        setupSupportedActionBarWithHome(toolbar);
        setActionBarTitle(R.string.title_edit_profile);

        User user = ParseManager.getInstance().getCurrentUser();
        name.setText(user.getFullName());
        email.setText(user.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_done:
                hideKeyboard();
                if (isAccountInfoChange()) {
                    editProfile();
                } else {
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected View getSnackBarAnchorView() {
        return accountInfoFormView;
    }

    /**
     * Attempts to sign in or register the account specified by the account_info form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual account_info attempt is made.
     */
    private void editProfile() {
        reset();

        // Store values at the time of the account_info attempt.
        String name = this.name.getText().toString();

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

        if (cancel) {
            // There was an error; don't attempt account_info and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user account_info attempt.
            presenter.editProfile(name);
        }
    }

    private void reset() {
        name.setError(null);
    }

    private boolean isAccountInfoChange() {
        User user = ParseManager.getInstance().getCurrentUser();
        if (!user.getFullName().equals(this.name.getText().toString())) {
            return true;
        }
        return false;
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 6;
    }

    @Override
    public void onRegisterComplete() {
    }

    @Override
    public void onLoginComplete() {
    }

    @Override
    public void onLogoutComplete() {
    }

    @Override
    public void onChangeProfilePictureComplete() {
    }

    @Override
    public void onEditProfileComplete() {
        showInfoSnackBar(R.string.message_edit_profile_complete);
        finish();
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

