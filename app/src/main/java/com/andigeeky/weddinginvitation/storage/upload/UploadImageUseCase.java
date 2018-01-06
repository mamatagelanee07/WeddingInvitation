package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.storage.UploadTask;

public class UploadImageUseCase {
    private ImageRepository imageRepository;

    public UploadImageUseCase(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public LiveData<Resource<UploadTask.TaskSnapshot>> uploadImages(String filePath) {
        return imageRepository.uploadImages(filePath);
    }
}
