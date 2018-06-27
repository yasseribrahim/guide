package com.azhar.university.guide.presentation.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.communicator.OnListInteractionListener;
import com.azhar.university.guide.domain.communicator.OnLogoutCallback;
import com.azhar.university.guide.domain.models.MoreItem;
import com.azhar.university.guide.domain.views.MoreView;
import com.azhar.university.guide.domain.views.ParseView;
import com.azhar.university.guide.presentation.presenters.more.MorePresenter;
import com.azhar.university.guide.presentation.presenters.more.MorePresenterImp;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenter;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenterImp;
import com.azhar.university.guide.presentation.ui.activities.AccountInfoActivity;
import com.azhar.university.guide.presentation.ui.adapters.MoreAdapter;
import com.azhar.university.guide.presentation.ui.custom.CustomDividerItemDecoration;
import com.azhar.university.guide.presentation.ui.utils.MoreIds;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInteractionListener}
 * interface.
 */
public class MoreFragment extends BaseFragment implements ParseView, MoreView, OnListInteractionListener<MoreItem> {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ParsePresenter parsePresenter;
    private MorePresenter morePresenter;
    private OnLogoutCallback callback;
    private MoreAdapter adapter;
    private List<MoreItem> items;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoreFragment() {
    }

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        ButterKnife.bind(this, view);

        parsePresenter = new ParsePresenterImp(this);
        morePresenter = new MorePresenterImp(this);
        items = new ArrayList<>();
        adapter = new MoreAdapter(items, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(getContext(), R.dimen.divider_mid);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        morePresenter.getMoreItems();

        return view;
    }

    @Override
    protected View getSnackBarAnchorView() {
        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnLogoutCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onListInteraction(MoreItem item) {
        switch (item.getId()) {
            case MoreIds.MORE_EDIT_PROFILE_ID:
                startActivity(new Intent(getContext(), AccountInfoActivity.class));
                break;
            case MoreIds.MORE_LOGOUT_ID:
                parsePresenter.logout();
                break;
        }
    }

    @Override
    public void onRegisterComplete() {

    }

    @Override
    public void onLoginComplete() {

    }

    @Override
    public void onEditProfileComplete() {

    }

    @Override
    public void onLogoutComplete() {
        callback.onLogoutCallback();
    }

    @Override
    public void showConnectionError(View.OnClickListener onClickListener) {
        showConnectionSnackBar(onClickListener);
    }

    @Override
    public void showError(String message, View.OnClickListener onClickListener) {
        showRetrySnackBar(message, onClickListener);
    }

    @Override
    public void unAuthorized() {

    }

    @Override
    public void onGetMoreItemsComplete(List<MoreItem> items) {
        this.items.clear();
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        parsePresenter.onDestroy();
        morePresenter.onDestroy();
    }
}
