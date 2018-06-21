package com.azhar.university.guide.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.utils.ParseManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DELAY_MILLIS = 2000;

    @BindView(R.id.containerView)
    RelativeLayout containerView;

    private Handler handler;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (ParseManager.getInstance().isUserLogin()) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        handler = new Handler();

        gotoApp();
    }

    private void gotoApp() {
        if (isGooglePlayServicesAvailable(this)) {
            handler.postDelayed(runnable, SPLASH_DELAY_MILLIS);
        } else {
            showRetrySnackBar(getString(R.string.msg_general_error), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoApp();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected View getSnackBarAnchorView() {
        return containerView;
    }
}
