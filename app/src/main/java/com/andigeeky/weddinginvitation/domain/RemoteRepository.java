package com.andigeeky.weddinginvitation.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;
import com.andigeeky.weddinginvitation.temp.Resource;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Completable;

public interface RemoteRepository {
    LiveData<Resource<AuthResult>> register(RegisterUserRequest request);
}
