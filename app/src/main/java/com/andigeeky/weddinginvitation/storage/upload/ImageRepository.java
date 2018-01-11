package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageRepository {

    @Inject
    public ImageRepository() {
    }

    public LiveData<Resource<UploadTask.TaskSnapshot>> uploadImages(String filePath) {
        return ImageService.getInstance().uploadImage(filePath);
    }
}
