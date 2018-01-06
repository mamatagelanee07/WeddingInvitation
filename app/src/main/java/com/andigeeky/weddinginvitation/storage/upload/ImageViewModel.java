package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageViewModel extends ViewModel {
    private UploadImageUseCase uploadImageUseCase;
    private MediatorLiveData<Resource<UploadTask.TaskSnapshot>> mediatorLiveData = new MediatorLiveData<>();
    private HashMap<String, Image> uploadSucessImages = new HashMap<>();
    private HashMap<String, Image> uploadFailedImages = new HashMap<>();

    ImageViewModel(UploadImageUseCase uploadImageUseCase) {
        this.uploadImageUseCase = uploadImageUseCase;
    }

    public void uploadImages(HashMap<String, Image> images) {
        LiveData<Resource<UploadTask.TaskSnapshot>> resourceLiveData = uploadImageUseCase.uploadImages(images.get(0));

        this.mediatorLiveData.addSource(resourceLiveData, new Observer<Resource<UploadTask.TaskSnapshot>>() {
            @Override
            public void onChanged(@Nullable Resource<UploadTask.TaskSnapshot> taskSnapshotResource) {
                if (taskSnapshotResource.status == Status.SUCCESS) {
                    uploadSuceessImages.add(taskSnapshotResource.data);
                }
                mediatorLiveData.setValue(taskSnapshotResource);
            }
        });
    }

    /**
     * Exposes the latest user status so the UI can observe it
     */
    public LiveData<Resource<UploadTask.TaskSnapshot>> getImages() {
        return mediatorLiveData;
    }
}
