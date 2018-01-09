package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.repository.RemoteRepository;
import com.andigeeky.weddinginvitation.repository.RemoteRepositoryDataStore;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

public class SignViewModel extends ViewModel {
    private RemoteRepositoryDataStore remoteRepository;
    private MediatorLiveData<Resource<AuthResult>> mediatorLiveData = new MediatorLiveData<>();

    @Inject
    SignViewModel(RemoteRepositoryDataStore remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public void registerUser(RegisterUserRequest request) {
        LiveData<Resource<AuthResult>> resourceLiveData = remoteRepository.register(request);
        this.mediatorLiveData.addSource(resourceLiveData, authResultResource -> mediatorLiveData.setValue(authResultResource));
    }

    /**
     * Exposes the latest user status so the UI can observe it
     */
    public LiveData<Resource<AuthResult>> getUser() {
        return mediatorLiveData;
    }
}
