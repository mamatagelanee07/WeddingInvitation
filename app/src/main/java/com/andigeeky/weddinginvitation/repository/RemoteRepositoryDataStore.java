package com.andigeeky.weddinginvitation.repository;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.RegisterUserService;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteRepositoryDataStore implements RemoteRepository {

    @Inject
    public RemoteRepositoryDataStore() {
    }

    @Override
    public LiveData<Resource<AuthResult>> register(RegisterUserRequest request) {
        return RegisterUserService.getInstance().registerUser(request);
    }
}
