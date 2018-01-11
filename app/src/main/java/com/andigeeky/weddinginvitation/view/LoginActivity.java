package com.andigeeky.weddinginvitation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.view.common.LoginNavigationController;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class LoginActivity extends BaseActivity {
    @Inject
    public LoginNavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null)
                navigationController.navigateToLogin();
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
