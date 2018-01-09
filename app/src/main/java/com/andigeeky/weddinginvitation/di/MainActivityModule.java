package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.view.MainActivity;
import com.andigeeky.weddinginvitation.view.SplashScreen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class, UploadActivityModule.class})
    abstract UploadActivity contributeUploadActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract SplashScreen contributeSplashActivity();
}
