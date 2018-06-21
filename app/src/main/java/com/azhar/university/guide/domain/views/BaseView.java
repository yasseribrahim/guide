package com.azhar.university.guide.domain.views;

import android.view.View;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface BaseView {
    void showConnectionError(View.OnClickListener onClickListener);

    void showError(String message, View.OnClickListener onClickListener);

    void showProgress();

    void hideProgress();

    void unAuthorized();
}
