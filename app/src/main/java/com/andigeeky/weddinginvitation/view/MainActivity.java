package com.andigeeky.weddinginvitation.view;

import android.os.Bundle;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.view.common.NavigationController;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    @Inject
    NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigationController.navigateToUser();
        }
    }
}
