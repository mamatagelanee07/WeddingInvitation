package com.andigeeky.weddinginvitation;

import android.app.Application;

import com.andigeeky.weddinginvitation.domain.service.jobs.JobManagerFactory;

import timber.log.Timber;


public class WeddingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        JobManagerFactory.getJobManager(this);
    }
}
