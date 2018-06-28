package com.azhar.university.guide;

import android.app.Application;

import com.azhar.university.guide.domain.modules.PreferencesModule;
import com.parse.Parse;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class UniversityGuideApplication extends Application {
    private static ApplicationComponent component;
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        Parse.initialize(new Parse.Configuration.Builder(this).
                applicationId(getString(R.string.back4app_app_id)).
                clientKey(getString(R.string.back4app_client_key)).
                server(getString(R.string.back4app_server_url)).
                build());

        buildDagger();
    }

    public void buildDagger() {
        component = DaggerApplicationComponent.
                builder().
                preferencesModule(new PreferencesModule()).
                build();
    }
}