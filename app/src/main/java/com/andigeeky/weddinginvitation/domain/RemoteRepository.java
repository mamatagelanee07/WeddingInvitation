package com.andigeeky.weddinginvitation.domain;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;

import io.reactivex.Completable;

public interface RemoteRepository {
    void register(RegisterUserRequest request);
}
