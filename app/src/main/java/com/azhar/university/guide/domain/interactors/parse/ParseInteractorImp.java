package com.azhar.university.guide.domain.interactors.parse;

import android.view.View;

import com.azhar.university.guide.domain.models.parse.User;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class ParseInteractorImp implements ParseInteractor {
    private ParseUser parse;

    public ParseInteractorImp() {
        this.parse = new ParseUser();
    }

    @Override
    public void register(final String email, final String password, final String fullName, final ParseCallbackStates callback) {
        parse.setUsername(email);
        parse.setPassword(password);
        parse.setEmail(email);
        parse.put(User.KEY_FULL_NAME, fullName);

        parse.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    callback.onRegisterComplete();
                } else {
                    callback.failure(e.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register(email, password, fullName, callback);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void login(final String email, final String password, final ParseCallbackStates callback) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    callback.onLoginComplete();
                } else {
                    callback.failure(e.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            login(email, password, callback);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void logout(final ParseCallbackStates callback) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    callback.onLogoutComplete();
                } else {
                    callback.failure(e.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logout(callback);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {
    }
}
