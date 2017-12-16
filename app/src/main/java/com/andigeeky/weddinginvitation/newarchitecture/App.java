package com.andigeeky.weddinginvitation.newarchitecture;

import android.app.Application;

import com.andigeeky.weddinginvitation.BuildConfig;

import timber.log.Timber;

/**
 * Created by Mamata on 12/16/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        JobManagerFactory.getJobManager(this);
    }
}
