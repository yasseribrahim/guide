package com.azhar.university.guide.domain.interactors.more;

import com.azhar.university.guide.domain.interactors.MainInteractor;
import com.azhar.university.guide.domain.models.MoreItem;

import java.util.List;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MoreInteractor extends MainInteractor {
    void getMoreItems(MoreCallbackStates callback);

    interface MoreCallbackStates extends CallbackStates {
        void onGetMoreItemsComplete(List<MoreItem> items);
    }
}
