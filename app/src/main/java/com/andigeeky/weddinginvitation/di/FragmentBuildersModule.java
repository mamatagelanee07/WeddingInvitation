package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.view.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();
}
