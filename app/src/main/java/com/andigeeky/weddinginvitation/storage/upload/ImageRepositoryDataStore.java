package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.storage.UploadTask;


public class ImageRepositoryDataStore implements ImageRepository {
    @Override
    public LiveData<Resource<UploadTask.TaskSnapshot>> uploadImages(String filePath) {
        return UploadImageService.getInstance().uploadImage(filePath);
    }
}
