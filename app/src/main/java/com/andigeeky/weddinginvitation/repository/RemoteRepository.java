package com.andigeeky.weddinginvitation.repository;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.auth.AuthResult;

public interface RemoteRepository {
    LiveData<Resource<AuthResult>> register(RegisterUserRequest request);
}
