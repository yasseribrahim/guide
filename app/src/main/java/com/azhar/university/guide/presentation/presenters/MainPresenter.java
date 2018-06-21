package com.azhar.university.guide.presentation.presenters;

import android.view.View;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MainPresenter extends LifeCycle {

    interface PresenterCallback {
        void success(Object data);

        void showError(String message, View.OnClickListener onClickListener);
    }
}
