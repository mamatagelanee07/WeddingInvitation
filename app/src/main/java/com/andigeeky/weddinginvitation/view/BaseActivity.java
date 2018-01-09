package com.andigeeky.weddinginvitation.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.windowstyleloadingview.dialog.SpotsDialog;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private AlertDialog loadingDialog;
    private ProgressDialog progressDialog;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .setTheme(R.style.LoadingDialog).build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
    }

    protected void showProgress(int max) {
        progressDialog.show();
        progressDialog.setMax(max);
    }

    protected void setProgressOfDialog(int progress) {
        progressDialog.setProgress(progress);
    }

    protected void startLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void stopLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
