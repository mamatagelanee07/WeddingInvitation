package com.andigeeky.weddinginvitation.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.windowstyleloadingview.dialog.SpotsDialog;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private AlertDialog loadingDialog;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
}
