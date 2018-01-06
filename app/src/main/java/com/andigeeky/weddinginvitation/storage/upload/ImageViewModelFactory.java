package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ImageViewModelFactory implements ViewModelProvider.Factory {

    private final UploadImageUseCase uploadImageUseCase;

    public ImageViewModelFactory(UploadImageUseCase uploadImageUseCase) {
        this.uploadImageUseCase = uploadImageUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ImageViewModel.class)) {
            return (T) new ImageViewModel(uploadImageUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
