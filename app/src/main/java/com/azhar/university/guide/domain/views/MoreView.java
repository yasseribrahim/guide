package com.azhar.university.guide.domain.views;

import com.azhar.university.guide.domain.models.MoreItem;

import java.util.List;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public interface MoreView extends BaseView {
    void onGetMoreItemsComplete(List<MoreItem> items);
}
