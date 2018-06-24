package com.azhar.university.guide.domain.utils;

import com.azhar.university.guide.domain.models.parse.User;
import com.parse.ParseUser;

/**
 * Created by Yasser.Ibrahim on 6/19/2018.
 */

public class ParseManager {
    private static final ParseManager MANAGER = new ParseManager();

    private ParseManager() {
    }

    public static ParseManager getInstance() {
        return MANAGER;
    }

    public boolean isUserLogin() {
        return ParseUser.getCurrentUser() != null;
    }

    public ParseUser getCurrentParseUser() {
        return ParseUser.getCurrentUser();
    }

    public User getCurrentUser() {
        return new User(getCurrentParseUser());
    }
}
