package com.andigeeky.weddinginvitation.storage.upload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andigeeky.weddinginvitation.R;
import com.haresh.multipleimagepickerlibrary.MultiImageSelector;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    private ArrayList<String> mSelectedImagesList = new ArrayList<>();
    private final int MAX_IMAGE_SELECTION_LIMIT = 10;
    private final int REQUEST_IMAGE = 301;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;

    private MultiImageSelector mMultiImageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mMultiImageSelector = MultiImageSelector.create();

        if (checkAndRequestPermissions()) {
            openChooseActivity();
        }
    }

    private void openChooseActivity() {
        mMultiImageSelector.showCamera(true);
        mMultiImageSelector.count(MAX_IMAGE_SELECTION_LIMIT);
        mMultiImageSelector.multi();
        mMultiImageSelector.origin(mSelectedImagesList);
        mMultiImageSelector.start(UploadActivity.this, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            try {
                mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            openChooseActivity();
        }
    }
}
