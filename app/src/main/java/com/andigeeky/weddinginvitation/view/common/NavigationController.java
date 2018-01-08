package com.andigeeky.weddinginvitation.view.common;

import android.support.v4.app.FragmentManager;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.view.MainActivity;
import com.andigeeky.weddinginvitation.view.user.UserFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToUser() {
        String tag = "user" + "/";
        UserFragment userFragment = UserFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, userFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
