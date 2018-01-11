package com.andigeeky.weddinginvitation.storage.upload;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.databinding.ActivityUploadBinding;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.andigeeky.weddinginvitation.firestore.AddImageService;
import com.andigeeky.weddinginvitation.view.BaseActivity;
import com.google.firebase.firestore.QuerySnapshot;
import com.haresh.multipleimagepickerlibrary.MultiImageSelector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class UploadActivity extends BaseActivity {
    private ArrayList<String> mSelectedImagesList = new ArrayList<>();
    private final int REQUEST_IMAGE = 301;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;

    @Inject
    ImageViewModel imageViewModel;
    @Inject
    MultiImageSelector mMultiImageSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityUploadBinding activityUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload);

        imageViewModel.getImages().observe(this, this::handleUploadImageResponse);

        activityUploadBinding.btnUploadImages.setOnClickListener(v -> {
            if (checkAndRequestPermissions()) {
                openChooseActivity();
            }
        });

        getImages();
    }

    private void getImages() {
        LiveData<Resource<QuerySnapshot>> images = AddImageService.getInstance().getImages();
        images.observe(this, querySnapshotResource -> {
            if (querySnapshotResource.status == Status.LOADING) {
                startLoading();
            } else if (querySnapshotResource.status == Status.SUCCESS) {
                // Apply to adapter
                stopLoading();
                if (querySnapshotResource.data.getDocuments().size() > 0) {
                    Toast.makeText(this, querySnapshotResource.data.getDocuments().size()+" images found..!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No images found..", Toast.LENGTH_SHORT).show();
                }
            } else if (querySnapshotResource.status == Status.ERROR) {
                stopLoading();
                Toast.makeText(this, "Error while getting images..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUploadImageResponse(UploadImageResponse uploadImageResponse) {
        if (uploadImageResponse != null) {
            int current = uploadImageResponse.getCurrentImageIndex();
            int size = mSelectedImagesList.size();
            if (uploadImageResponse.getResult().status == Status.LOADING) {
                Toast.makeText(UploadActivity.this,
                        current + 1 + "out of " + size + " uploading", Toast.LENGTH_SHORT).show();

                int progress = (current + 1) * 100 / size;
                setProgressOfDialog(progress);
            }

            if (current == size - 1 && (uploadImageResponse.getResult().status == Status.SUCCESS ||
                    uploadImageResponse.getResult().status == Status.ERROR)) {
                stopLoading();
                Toast.makeText(UploadActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).
                        show();
                // Store in database
                storeImageUrlInDatabase(imageViewModel.getSuccessfullyUploadedImages());
            }
        }
    }

    private void storeImageUrlInDatabase(ArrayList<Image> images) {

    }

    private void openChooseActivity() {
        mMultiImageSelector.showCamera(true);
        int MAX_IMAGE_SELECTION_LIMIT = 10;
        mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
        mMultiImageSelector.multi();
        mMultiImageSelector.origin(mSelectedImagesList);
        mMultiImageSelector.start(UploadActivity.this, REQUEST_IMAGE);
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
        showProgress(100);
//        startLoading();
        imageViewModel.uploadImages(ImageUtils.getImages(mSelectedImagesList));
    }

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
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
}
