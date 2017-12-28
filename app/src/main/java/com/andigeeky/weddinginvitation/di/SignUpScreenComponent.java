package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.view.SignUpScreen;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by E066733 on 12/28/2017.
 */
@Subcomponent(modules = SignUpScreenModule.class)
public interface SignUpScreenComponent extends AndroidInjector<SignUpScreen> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SignUpScreen> {
    }
}
