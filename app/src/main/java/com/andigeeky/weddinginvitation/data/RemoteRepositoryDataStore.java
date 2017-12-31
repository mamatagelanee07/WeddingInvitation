package com.andigeeky.weddinginvitation.data;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.RegisterUserService;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.auth.AuthResult;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public LiveData<Resource<AuthResult>> register(RegisterUserRequest request) {
        return RegisterUserService.getInstance().registerUser(request);
    }
}
