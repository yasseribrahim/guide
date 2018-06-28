package com.azhar.university.guide.domain.utils;

import android.content.SharedPreferences;

import com.azhar.university.guide.domain.models.parse.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Yasser.Ibrahim on 6/28/2018.
 */

public class UserManager {
    private static final UserManager MANAGER = new UserManager();

    private volatile static User user;

    private UserManager() {
    }

    /**
     * Returns singleton class instance
     */
    public static UserManager getManager() {
        return MANAGER;
    }

    public Boolean isExist(SharedPreferences preferences) {
        if (getUser(preferences) != null)
            return true;
        else
            return false;
    }

    private void buildUser(SharedPreferences preferences) {
        if (preferences != null) {
            String json = preferences.getString(Constants.KEY_USER_LOGGED_OBJECT, null);
            try {
                user = new Gson().fromJson(json, new TypeToken<User>() {}.getType());
            } catch (Exception ex) {

            }
        }
    }

    public void saveUser(SharedPreferences preferences, User user) {
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.KEY_USER_LOGGED_OBJECT, new Gson().toJson(user));
            editor.apply();
            buildUser(preferences);
        }
    }

    public User getUser(SharedPreferences preferences) {
        if (user == null) {
            synchronized (User.class) {
                if (user == null) {
                    buildUser(preferences);
                }
            }
        }
        return user;
    }
}
