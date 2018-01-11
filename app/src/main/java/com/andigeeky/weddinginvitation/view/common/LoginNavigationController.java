package com.andigeeky.weddinginvitation.view.common;

import android.support.v4.app.FragmentManager;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.view.LoginActivity;
import com.andigeeky.weddinginvitation.view.fragments.ResetPasswordFragment;
import com.andigeeky.weddinginvitation.view.fragments.LoginFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link LoginActivity}.
 */
public class LoginNavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public LoginNavigationController(LoginActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToLogin() {
        String tag = "register" + "/";
        LoginFragment loginFragment = LoginFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToForgotPassword() {
        String tag = "forgot_password" + "/";
        ResetPasswordFragment forgotPasswordFragment = ResetPasswordFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, forgotPasswordFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
