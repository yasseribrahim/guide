package com.azhar.university.guide.presentation.presenters.parse;

import com.azhar.university.guide.presentation.presenters.MainPresenter;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface ParsePresenter extends MainPresenter {
    void register(String email, String password, String fullName);

    void login(String email, String password);

    void logout();
}
