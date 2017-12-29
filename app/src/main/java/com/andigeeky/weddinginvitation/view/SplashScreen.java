package com.andigeeky.weddinginvitation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.andigeeky.weddinginvitation.R;

public class SplashScreen extends BaseActivity {

    private Handler mHandler;
    private Runnable mRunnable;
    private static final long SPLASH_DURATION = 2500L;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mRunnable = () -> {
            startActivity(new Intent(SplashScreen.this, SignUpScreen.class));
            finish();
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, SPLASH_DURATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
}
