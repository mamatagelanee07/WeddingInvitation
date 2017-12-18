package com.andigeeky.weddinginvitation.data;

import com.andigeeky.weddinginvitation.domain.RemoteRepository;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.RegisterUserService;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public void register(RegisterUserRequest request) {
        RegisterUserService.getInstance().registerUser(request);
    }
}
