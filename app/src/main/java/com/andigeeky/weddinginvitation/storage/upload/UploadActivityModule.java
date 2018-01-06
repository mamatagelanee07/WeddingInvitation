package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.ViewModelProviders;

import com.haresh.multipleimagepickerlibrary.MultiImageSelector;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class UploadActivityModule {
    @Provides
    ImageViewModelFactory provideImageViewModelFactory(UploadImageUseCase uploadImageUseCase) {
        return new ImageViewModelFactory(uploadImageUseCase);
    }

    @Provides
    UploadImageUseCase provideUploadImageUseCase(ImageRepository imageRepository) {
        return new UploadImageUseCase(imageRepository);
    }

    @Provides
    ImageViewModel provideImageViewModel(UploadActivity activity, ImageViewModelFactory imageViewModelFactory) {
        return ViewModelProviders.of(activity, imageViewModelFactory).get(ImageViewModel.class);
    }

    @Provides
    MultiImageSelector provideMultipleImageSelector() {
        return MultiImageSelector.create();
    }
}
