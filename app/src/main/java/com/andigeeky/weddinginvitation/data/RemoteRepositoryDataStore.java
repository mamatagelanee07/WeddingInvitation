package com.andigeeky.weddinginvitation.data;

import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.domain.RemoteRepository;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;
import com.andigeeky.weddinginvitation.domain.service.networking.RegisterUserService;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public MutableLiveData<RegisterUserResponse> register(RegisterUserRequest request) {
        return RegisterUserService.getInstance().registerUser(request);
    }
}
