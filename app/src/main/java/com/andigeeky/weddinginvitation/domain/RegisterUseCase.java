package com.andigeeky.weddinginvitation.domain;

import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;

import io.reactivex.Completable;

public class RegisterUseCase {
    private RemoteRepositoryDataStore remoteRepositoryDataStore;

    public RegisterUseCase(RemoteRepositoryDataStore remoteRepositoryDataStore) {
        this.remoteRepositoryDataStore = remoteRepositoryDataStore;
    }

    public Completable registerUser(User comment) {
        return remoteRepositoryDataStore.register(comment);
    }
}
