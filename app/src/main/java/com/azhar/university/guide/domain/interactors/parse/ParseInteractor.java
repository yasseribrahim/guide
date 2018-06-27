package com.azhar.university.guide.domain.interactors.parse;

import com.azhar.university.guide.domain.interactors.MainInteractor;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface ParseInteractor extends MainInteractor {
    void register(String email, String password, String fullName, ParseCallbackStates callback);

    void login(String email, String password, ParseCallbackStates callback);

    void logout(ParseCallbackStates callback);

    void editProfile(String fullName, ParseCallbackStates callback);

    interface ParseCallbackStates extends CallbackStates {
        void onRegisterComplete();

        void onLoginComplete();

        void onLogoutComplete();

        void onEditProfileComplete();
    }
}
