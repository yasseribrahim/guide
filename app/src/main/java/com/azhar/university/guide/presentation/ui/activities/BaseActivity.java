package com.azhar.university.guide.presentation.ui.activities;

import com.azhar.university.guide.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract View getSnackBarAnchorView();

    public Snackbar showRetrySnackBar(String message, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.label_retry, clickListener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showConnectionSnackBar(View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), R.string.msg_error_connection, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.label_retry, clickListener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showInfoSnackBar(@StringRes int message) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showInfoSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getSnackBarAnchorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}

