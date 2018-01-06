package com.andigeeky.weddinginvitation.storage.upload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.databinding.ActivityUploadBinding;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.andigeeky.weddinginvitation.view.BaseActivity;
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
    ImageViewModelFactory imageViewModelFactory;
    @Inject
    ImageViewModel imageViewModel;
    @Inject
    MultiImageSelector mMultiImageSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityUploadBinding activityUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload);

        imageViewModel.getImages().observe(this, uploadImageResponse -> {
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
                }
            }
        });

        activityUploadBinding.btnUploadImages.setOnClickListener(v -> {
            if (checkAndRequestPermissions()) {
                openChooseActivity();
            }
        });
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
