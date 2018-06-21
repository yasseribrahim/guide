package com.azhar.university.guide.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.communicator.OnLogoutCallback;
import com.azhar.university.guide.presentation.ui.fragments.MoreFragment;
import com.parse.ParseInstallation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnLogoutCallback {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:

                return true;
            case R.id.navigation_dashboard:

                return true;
            case R.id.navigation_notifications:

                return true;
            case R.id.navigation_more:
                replace(MoreFragment.newInstance());
                return true;
        }
        return false;
    }

    private void replace(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLogoutCallback() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
