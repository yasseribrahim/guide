package com.azhar.university.guide.presentation.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azhar.university.guide.R;
import com.azhar.university.guide.domain.communicator.OnListInteractionListener;
import com.azhar.university.guide.domain.communicator.OnLogoutCallback;
import com.azhar.university.guide.domain.models.MoreItem;
import com.azhar.university.guide.domain.utils.Utils;
import com.azhar.university.guide.domain.views.MoreView;
import com.azhar.university.guide.domain.views.ParseView;
import com.azhar.university.guide.presentation.presenters.more.MorePresenter;
import com.azhar.university.guide.presentation.presenters.more.MorePresenterImp;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenter;
import com.azhar.university.guide.presentation.presenters.parse.ParsePresenterImp;
import com.azhar.university.guide.presentation.ui.activities.AccountInfoActivity;
import com.azhar.university.guide.presentation.ui.adapters.MoreAdapter;
import com.azhar.university.guide.presentation.ui.custom.CustomDividerItemDecoration;
import com.azhar.university.guide.domain.utils.Constants;
import com.azhar.university.guide.presentation.ui.utils.MoreIds;
import com.azhar.university.guide.presentation.ui.utils.RequestsPermissionCodes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInteractionListener}
 * interface.
 */
public class MoreFragment extends BaseFragment implements ParseView, MoreView, OnListInteractionListener<MoreItem> {
    @BindView(R.id.profile_image)
    ImageView profilePic;
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

    @OnClick(R.id.profile_image)
    public void showEditProfilePic() {

        String[] optionsList = getActivity().getResources().getStringArray(R.array.profile_image_options);

        ContextThemeWrapper cw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(cw);
        builder.setTitle(this.getString(R.string.title_dialog_profile_image));
        builder.setItems(optionsList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        checkCameraPermission();
                        break;
                    case 1:
                        startGallery();
                        break;
                    case 2:
                        profilePic.setImageResource(R.drawable.ic_empty_person);
                        break;
                    case 3:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestsPermissionCodes.REQUEST_CAMERA_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    if (profilePic == null) {
                        showToastShort(getActivity().getString(R.string.message_general_error));
                        return;
                    }

                    int imageSize = Utils.getFileSizeFromFile(new File(imageFile));
                    if (imageSize <= Constants.MAXIMUM_IMAGE_SIZE) {
                        if (imageFile != null) {
                            Bitmap bitmap = getCorrectImage(imageFile);
                            Bitmap bitmapSmall = Bitmap.createScaledBitmap(bitmap, profilePic.getWidth(), profilePic.getHeight(), false);
                            profilePic.setImageBitmap(bitmapSmall);
                            galleryAddPic();

                            parsePresenter.changeProfilePicture(new File(imageFile));
                        } else {
                            showToastShort(getActivity().getString(R.string.message_general_error));
                        }

                    } else {
                        showToastShort(getActivity().getString(R.string.message_profile_image_max_size));
                    }
                }
                break;

            case RequestsPermissionCodes.REQUEST_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    try {
                        final Bitmap bitmapProfile = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        if (bitmapProfile != null) {
                            int imageSize = Utils.getFileSizeFromUri(getActivity(), uri);
                            if (imageSize <= Constants.MAXIMUM_IMAGE_SIZE) {
                                profilePic.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        profilePic.setImageBitmap(bitmapProfile);
                                    }
                                });

                                parsePresenter.changeProfilePicture(new File(uri.getPath()));
                            } else {
                                showToastShort(getActivity().getString(R.string.message_profile_image_max_size));
                            }
                        }
                    } catch (IOException e) {
                        showToastShort(getActivity().getString(R.string.message_general_error));
                    }
                }
                break;
        }
    }

    private Bitmap getCorrectImage(String photoPath) {
        ExifInterface anInterface = null;
        try {
            anInterface = new ExifInterface(photoPath);
        } catch (IOException e) {
            return null;
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        int orientation = anInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(bitmap, 270);
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;

            default:
                break;
        }

        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void galleryAddPic() {
        if (imageFile != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File(imageFile));
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
        }
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
    public void onChangeProfilePictureComplete() {

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
