package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.view.SignUpScreen;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = SignUpScreenModule.class)
public interface SignUpScreenComponent extends AndroidInjector<SignUpScreen> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SignUpScreen> {
    }
}
