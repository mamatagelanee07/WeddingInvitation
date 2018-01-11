package com.andigeeky.weddinginvitation.firestore;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.andigeeky.weddinginvitation.domain.service.networking.common.InstantAppExecutors;
import com.andigeeky.weddinginvitation.domain.service.networking.common.NetworkBoundResource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class AddImageService {
    private static AddImageService instance;
    private final FirebaseFirestore fireStore;

    public AddImageService() {
        this.fireStore = FirebaseFirestore.getInstance();
    }

    public static synchronized AddImageService getInstance() {
        if (instance == null) {
            instance = new AddImageService();
        }
        return instance;
    }

    public LiveData<Resource<Void>> addImages(ArrayList<ImageVO> images) {
        return new NetworkBoundResource<Void>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<Void>> createCall() {
                return getAddImagesTask(images);
            }
        }.asLiveData();
    }

    public LiveData<Resource<QuerySnapshot>> getImages() {
        return new NetworkBoundResource<QuerySnapshot>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<QuerySnapshot>> createCall() {
                return getImagesTask();
            }
        }.asLiveData();
    }

    private LiveData<Task<Void>> getAddImagesTask(ArrayList<ImageVO> images) {
        MutableLiveData<Task<Void>> liveTask = new MutableLiveData<>();
        WriteBatch writeBatch = fireStore.batch();

        for (ImageVO imageVO : images) {
            writeBatch.set(fireStore.collection("wedvites").document("images").collection("images").document(), imageVO);
        }

        Task<Void> commit = writeBatch.commit();
        commit.addOnCompleteListener(liveTask::setValue);

        return liveTask;
    }

    private LiveData<Task<QuerySnapshot>> getImagesTask() {
        MutableLiveData<Task<QuerySnapshot>> liveTask = new MutableLiveData<>();
        Task<QuerySnapshot> users = fireStore.collection("wedvites").document("images").collection("images").get();
        users.addOnCompleteListener(liveTask::setValue);
        return liveTask;
    }
}