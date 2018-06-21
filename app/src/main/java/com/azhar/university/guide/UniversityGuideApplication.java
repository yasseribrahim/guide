package com.azhar.university.guide;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class UniversityGuideApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this).
                        applicationId(getString(R.string.back4app_app_id)).
                        clientKey(getString(R.string.back4app_client_key)).
                        server(getString(R.string.back4app_server_url)).
                        build());
    }
}
