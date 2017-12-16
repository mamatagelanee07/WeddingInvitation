package com.andigeeky.weddinginvitation.domain;

import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;

import io.reactivex.Completable;

public class RegisterUseCase {
    private RemoteRepositoryDataStore remoteRepositoryDataStore;

    public RegisterUseCase(RemoteRepositoryDataStore remoteRepositoryDataStore) {
        this.remoteRepositoryDataStore = remoteRepositoryDataStore;
    }

    public Completable registerUser(RegisterUserRequest request) {
        return remoteRepositoryDataStore.register(request);
    }
}
