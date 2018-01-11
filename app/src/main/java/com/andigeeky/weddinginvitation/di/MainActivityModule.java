package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.view.LoginActivity;
import com.andigeeky.weddinginvitation.view.SplashScreen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class})
    abstract UploadActivity contributeUploadActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract SplashScreen contributeSplashActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract LoginActivity contributeLoginActivity();
}
