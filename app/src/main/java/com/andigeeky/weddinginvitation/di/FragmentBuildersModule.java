package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.view.fragments.GalleryFragment;
import com.andigeeky.weddinginvitation.view.fragments.ResetPasswordFragment;
import com.andigeeky.weddinginvitation.view.fragments.LoginFragment;
import com.andigeeky.weddinginvitation.view.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract ResetPasswordFragment contributeResetPasswordFragment();

    @ContributesAndroidInjector
    abstract GalleryFragment contributeGalleryFragment();
}
