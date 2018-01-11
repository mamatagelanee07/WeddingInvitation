package com.andigeeky.weddinginvitation.view.fragments;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.binding.FragmentDataBindingComponent;
import com.andigeeky.weddinginvitation.common.FacebookUtils;
import com.andigeeky.weddinginvitation.common.GoogleUtils;
import com.andigeeky.weddinginvitation.common.utility.RegisterRequestMapper;
import com.andigeeky.weddinginvitation.common.utility.ValidationUtils;
import com.andigeeky.weddinginvitation.databinding.FragmentGalleryBinding;
import com.andigeeky.weddinginvitation.di.Injectable;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.andigeeky.weddinginvitation.firestore.AddImageService;
import com.andigeeky.weddinginvitation.presentation.RegisterUserViewModel;
import com.andigeeky.weddinginvitation.storage.upload.Image;
import com.andigeeky.weddinginvitation.storage.upload.ImageUtils;
import com.andigeeky.weddinginvitation.storage.upload.ImageViewModel;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.storage.upload.UploadImageResponse;
import com.andigeeky.weddinginvitation.view.BaseActivity;
import com.andigeeky.weddinginvitation.view.LoginActivity;
import com.andigeeky.weddinginvitation.view.adapter.GalleryAdapter;
import com.andigeeky.weddinginvitation.view.adapter.SpacesItemDecoration;
import com.andigeeky.weddinginvitation.view.vo.Action;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.andigeeky.weddinginvitation.view.vo.Login;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.QuerySnapshot;
import com.haresh.multipleimagepickerlibrary.MultiImageSelector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment implements Injectable {

    private ArrayList<String> mSelectedImagesList = new ArrayList<>();
    private final int REQUEST_IMAGE = 301;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private FragmentGalleryBinding dataBinding;

    @Inject
    ImageViewModel imageViewModel;
    private MultiImageSelector mMultiImageSelector = MultiImageSelector.create();

    public static GalleryFragment create() {
        return new GalleryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery,
                container, false, dataBindingComponent);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataBinding.galleryGrid.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        GalleryAdapter adapter = new GalleryAdapter(getContext());
        dataBinding.galleryGrid.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(32);
        dataBinding.galleryGrid.addItemDecoration(decoration);

       /* getImages();
        imageViewModel.getImages().observe(this, this::handleUploadImageResponse);*/
    }

    private void getImages() {
        LiveData<Resource<QuerySnapshot>> images = AddImageService.getInstance().getImages();
        images.observe(this, querySnapshotResource -> {
            if (querySnapshotResource.status == Status.LOADING) {
                ((BaseActivity) getActivity()).startLoading();
            } else if (querySnapshotResource.status == Status.SUCCESS) {
                // Apply to adapter
                ((BaseActivity) getActivity()).stopLoading();
                if (querySnapshotResource.data.getDocuments().size() > 0) {
                    Toast.makeText(getActivity(), querySnapshotResource.data.getDocuments().size() + " images found..!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No images found..", Toast.LENGTH_SHORT).show();
                }
            } else if (querySnapshotResource.status == Status.ERROR) {
                ((BaseActivity) getActivity()).stopLoading();
                Toast.makeText(getActivity(), "Error while getting images..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUploadImageResponse(UploadImageResponse uploadImageResponse) {
        if (uploadImageResponse != null) {
            int current = uploadImageResponse.getCurrentImageIndex();
            int size = mSelectedImagesList.size();
            if (uploadImageResponse.getResult().status == Status.LOADING) {
                Toast.makeText(getActivity(),
                        current + 1 + "out of " + size + " uploading", Toast.LENGTH_SHORT).show();

                int progress = (current + 1) * 100 / size;
                ((BaseActivity) getActivity()).setProgressOfDialog(progress);
            }

            if (current == size - 1 && (uploadImageResponse.getResult().status == Status.SUCCESS ||
                    uploadImageResponse.getResult().status == Status.ERROR)) {
                ((BaseActivity) getActivity()).stopLoading();
                Toast.makeText(getActivity(), "Successfully uploaded", Toast.LENGTH_SHORT).
                        show();
                // Store in database
                storeImageUrlInDatabase(imageViewModel.getSuccessfullyUploadedImages());
            }
        }
    }


    private void storeImageUrlInDatabase(ArrayList<Image> images) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE) {
            mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            uploadImages();
        }
    }

    private void uploadImages() {
        ((BaseActivity) getActivity()).showProgress(100);
        imageViewModel.uploadImages(ImageUtils.getImages(mSelectedImagesList));
    }


    private void openChooseActivity() {
        mMultiImageSelector.showCamera(true);
        int MAX_IMAGE_SELECTION_LIMIT = 10;
        mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
        mMultiImageSelector.multi();
        mMultiImageSelector.origin(mSelectedImagesList);
        mMultiImageSelector.start(getActivity(), REQUEST_IMAGE);
    }

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            openChooseActivity();
        }
    }

    public class Handler {
        public void onClickGoogle(View view) {
            if (checkAndRequestPermissions()) {
                openChooseActivity();
            }
        }
    }
}
