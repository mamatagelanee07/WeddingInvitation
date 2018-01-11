package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

;
import com.andigeeky.weddinginvitation.domain.service.networking.common.InstantAppExecutors;
import com.andigeeky.weddinginvitation.domain.service.networking.common.NetworkBoundResource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageService {
    private static ImageService instance;
    private final FirebaseStorage firebaseStorage;

    public ImageService() {
        this.firebaseStorage = FirebaseStorage.getInstance();
    }

    public static synchronized ImageService getInstance() {
        if (instance == null) {
            instance = new ImageService();
        }
        return instance;
    }

    public LiveData<Resource<UploadTask.TaskSnapshot>> uploadImage(String filePath) {
        return new NetworkBoundResource<UploadTask.TaskSnapshot>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<UploadTask.TaskSnapshot>> createCall() {
                return getUploadImageTask(filePath);
            }
        }.asLiveData();
    }

    private LiveData<Task<UploadTask.TaskSnapshot>> getUploadImageTask(String filePath) {
        MutableLiveData<Task<UploadTask.TaskSnapshot>> liveTask = new MutableLiveData<>();
        UploadTask uploadTask = null;
        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference imagesRef = storageRef.child("wedding");
        StorageReference spaceRef = imagesRef.child(Uri.parse(filePath).getLastPathSegment());

        try {
            InputStream stream = new FileInputStream(new File(filePath));
            uploadTask = spaceRef.putStream(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (uploadTask != null) {
            uploadTask.addOnCompleteListener(liveTask::setValue);
        }
        return liveTask;
    }
}