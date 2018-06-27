package com.azhar.university.guide.presentation.presenters.more;

import android.view.View;

import com.azhar.university.guide.domain.interactors.more.MoreInteractor;
import com.azhar.university.guide.domain.interactors.more.MoreInteractorImp;
import com.azhar.university.guide.domain.models.MoreItem;
import com.azhar.university.guide.domain.views.MoreView;

import java.util.List;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class MorePresenterImp implements MorePresenter, MoreInteractor.MoreCallbackStates {
    private MoreView view;
    private MoreInteractor interactor;

    public MorePresenterImp(MoreView view) {
        this.view = view;
        this.interactor = new MoreInteractorImp();
    }

    @Override
    public void getMoreItems() {
        interactor.getMoreItems(this);
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
    public void failure(String message, View.OnClickListener onClickListener) {
        if (view != null) {
            view.showError(message, onClickListener);
        }
    }

    @Override
    public void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    @Override
    public void hideProgress() {
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public void unAuthorized() {
        if (view != null) {
            view.unAuthorized();
        }
    }

    @Override
    public void onGetMoreItemsComplete(List<MoreItem> items) {
        if (view != null) {
            view.onGetMoreItemsComplete(items);
        }
    }
}
