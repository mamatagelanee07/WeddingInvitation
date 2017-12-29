package com.andigeeky.weddinginvitation.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;
import com.andigeeky.weddinginvitation.temp.Resource;
import com.google.firebase.auth.AuthResult;

public class RegisterUseCase {
    private RemoteRepository remoteRepositoryDataStore;

    public RegisterUseCase(RemoteRepository remoteRepositoryDataStore) {
        this.remoteRepositoryDataStore = remoteRepositoryDataStore;
    }

    public LiveData<Resource<AuthResult>> registerUser(RegisterUserRequest request) {
        return remoteRepositoryDataStore.register(request);
    }
}
