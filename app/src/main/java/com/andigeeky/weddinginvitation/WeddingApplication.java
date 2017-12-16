package com.andigeeky.weddinginvitation;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
