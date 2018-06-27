package com.azhar.university.guide.domain.interactors.more;

import android.view.View;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.interactors.BaseRxInteractor;
import com.azhar.university.guide.domain.models.MoreItem;
import com.azhar.university.guide.presentation.ui.utils.MoreIds;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class MoreInteractorImp extends BaseRxInteractor implements MoreInteractor {
    public MoreInteractorImp() {
    }

    @Override
    public void getMoreItems(final MoreCallbackStates callback) {
        Observable<List<MoreItem>> observable = Observable.create(new ObservableOnSubscribe<List<MoreItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MoreItem>> emitter) throws Exception {
                callback.showProgress();
                List<MoreItem> items = new ArrayList<>();
                items.add(new MoreItem(MoreIds.MORE_EDIT_PROFILE_ID, R.string.more_edit_profile, R.drawable.ic_edit_profile));
                items.add(new MoreItem(MoreIds.MORE_LOGOUT_ID, R.string.more_log_out, R.drawable.ic_logout));
                emitter.onNext(items);
            }
        });
        prepare(observable, new BaseObserver<List<MoreItem>>(callback) {
            @Override
            public void onNext(List<MoreItem> items) {
                super.onNext(items);
                callback.onGetMoreItemsComplete(items);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.failure(e.getMessage(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMoreItems(callback);
                    }
                });
            }
        });
    }
}
