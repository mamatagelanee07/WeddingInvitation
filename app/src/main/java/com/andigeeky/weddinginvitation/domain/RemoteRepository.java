package com.andigeeky.weddinginvitation.domain;

import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;

import io.reactivex.Completable;

public interface RemoteRepository {
    MutableLiveData<RegisterUserResponse> register(RegisterUserRequest request);
}
