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

    public void storeUser(ParseUser parseUser) {
        User user = new User(parseUser);
//        UserManager.getManager().saveUser(user);
    }

    public User getCurrentUser() {
        return new User(getCurrentParseUser());
    }

    public void refreshCurrentUser() {
        try {
            ParseUser user = getCurrentParseUser();
            if (user != null) {
                user.fetch();
            }
        } catch (Exception ex) {
//            if (ex instanceof ParseException) {
//                ParseException exception = (ParseException) ex;
//                if (exception.getCode() == ParseException.INVALID_SESSION_TOKEN ||
//                        exception.getCode() == ParseException.SESSION_MISSING ||
//                        exception.getCode() == ParseException.INVALID_LINKED_SESSION) {
//                    ParseUser.logOut();
//                }
//            }
            ex.printStackTrace();
        }
    }
}
