package com.andigeeky.weddinginvitation.storage.upload;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = UploadActivityModule.class)
public interface UploadActivityComponent extends AndroidInjector<UploadActivity> {
    @Subcomponent.Builder
    abstract class MainBuilder extends AndroidInjector.Builder<UploadActivity> {
    }
}
