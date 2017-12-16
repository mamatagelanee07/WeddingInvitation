package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;

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
