package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.storage.UploadTask;

public interface ImageRepository {
    LiveData<Resource<UploadTask.TaskSnapshot>> uploadImages(String filePath);
}
