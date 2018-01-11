package com.andigeeky.weddinginvitation.view.common;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.view.fragments.GalleryFragment;
import com.andigeeky.weddinginvitation.view.user.UserFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link AppCompatActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(UploadActivity uploadActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = uploadActivity.getSupportFragmentManager();
    }

    public void navigateToUser() {
        String tag = "user" + "/";
        UserFragment userFragment = UserFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, userFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToGallery() {
        String tag = "gallery" + "/";
        GalleryFragment galleryFragment = GalleryFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, galleryFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
