package com.azhar.university.guide.presentation.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.azhar.university.guide.UniversityGuideApplication;

/**
 * Created by Yasser.Ibrahim on 6/28/2018.
 */

public class Utils {

    public static boolean isConnectingToInternet() {
        if (UniversityGuideApplication.getApplication().getApplicationContext() != null) {

            ConnectivityManager cm = (ConnectivityManager) UniversityGuideApplication.getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
