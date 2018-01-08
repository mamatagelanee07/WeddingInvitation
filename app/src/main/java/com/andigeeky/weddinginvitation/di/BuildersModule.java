package com.andigeeky.weddinginvitation.di;

import android.app.Activity;

import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivityComponent;
import com.andigeeky.weddinginvitation.view.SignUpScreen;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(SignUpScreen.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSignUpScreen(SignUpScreenComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(UploadActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindUploadActivity(UploadActivityComponent.MainBuilder builder);

}
