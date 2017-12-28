package com.andigeeky.weddinginvitation.domain;

import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;

public class RegisterUseCase {
    private RemoteRepository remoteRepositoryDataStore;

    public RegisterUseCase(RemoteRepository remoteRepositoryDataStore) {
        this.remoteRepositoryDataStore = remoteRepositoryDataStore;
    }

    public MutableLiveData<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        return remoteRepositoryDataStore.register(request);
    }
}
