package com.andigeeky.weddinginvitation.storage.upload;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.view.BaseActivity;
import com.andigeeky.weddinginvitation.view.common.NavigationController;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class UploadActivity extends BaseActivity {
    @Inject
    NavigationController navigationController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_upload);

        if (savedInstanceState == null) {
            navigationController.navigateToGallery();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
