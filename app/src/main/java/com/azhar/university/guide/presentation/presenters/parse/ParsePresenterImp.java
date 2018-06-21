package com.azhar.university.guide.presentation.presenters.parse;

import android.view.View;

import com.azhar.university.guide.domain.interactors.parse.ParseInteractor;
import com.azhar.university.guide.domain.interactors.parse.ParseInteractorImp;
import com.azhar.university.guide.domain.views.ParseView;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class ParsePresenterImp implements ParsePresenter, ParseInteractor.ParseCallbackStates {
    private ParseView view;
    private ParseInteractor interactor;

    public ParsePresenterImp(ParseView view) {
        this.view = view;
        this.interactor = new ParseInteractorImp();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.onDestroy();
    }

    @Override
    public void register(String email, String password, String fullName) {
        interactor.register(email, password, fullName, this);
    }

    @Override
    public void login(String email, String password) {
        interactor.login(email, password, this);
    }

    @Override
    public void logout() {
        interactor.logout(this);
    }

    @Override
    public void failure(String message, View.OnClickListener onClickListener) {
        view.showError(message, onClickListener);
    }

    @Override
    public void showProgress() {
        view.showProgress();
    }

    @Override
    public void hideProgress() {
        view.hideProgress();
    }

    @Override
    public void unAuthorized() {
        view.unAuthorized();
    }

    @Override
    public void onRegisterComplete() {
        view.onRegisterComplete();
    }

    @Override
    public void onLoginComplete() {
        view.onLoginComplete();
    }

    @Override
    public void onLogoutComplete() {
        view.onLogoutComplete();
    }
}
