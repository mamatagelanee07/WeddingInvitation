package com.andigeeky.weddinginvitation.di;

import com.haresh.multipleimagepickerlibrary.MultiImageSelector;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class UploadActivityModule {
    @Provides
    MultiImageSelector provideMultipleImageSelector() {
        return MultiImageSelector.create();
    }
}
