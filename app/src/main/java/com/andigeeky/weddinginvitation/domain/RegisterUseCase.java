package com.andigeeky.weddinginvitation.domain;

import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;

public class RegisterUseCase {
    private RemoteRepositoryDataStore remoteRepositoryDataStore;

    public RegisterUseCase(RemoteRepositoryDataStore remoteRepositoryDataStore) {
        this.remoteRepositoryDataStore = remoteRepositoryDataStore;
    }

    public void registerUser(RegisterUserRequest request) {
        remoteRepositoryDataStore.register(request);
    }
}
