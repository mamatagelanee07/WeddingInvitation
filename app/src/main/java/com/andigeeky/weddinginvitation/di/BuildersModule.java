package com.andigeeky.weddinginvitation.di;

import android.app.Activity;

import com.andigeeky.weddinginvitation.view.LoginActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSignUpScreen(SignUpScreenComponent.Builder builder);
}
