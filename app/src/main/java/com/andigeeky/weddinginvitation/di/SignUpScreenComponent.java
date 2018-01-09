package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.view.LoginActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = SignUpScreenModule.class)
public interface SignUpScreenComponent extends AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<LoginActivity> {
    }
}
