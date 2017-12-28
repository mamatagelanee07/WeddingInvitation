package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;

public class UserViewModel extends ViewModel {
    private RegisterUseCase registerUseCase;
    private MediatorLiveData<RegisterUserResponse> userLiveData = new MediatorLiveData<>();


    public UserViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void registerUser(RegisterUserRequest request) {
        MutableLiveData<RegisterUserResponse> response = registerUseCase.registerUser(request);
        userLiveData.addSource(response, registerUserResponse -> {
            userLiveData.postValue(registerUserResponse);
            userLiveData.removeSource(response);
        });
    }

    /**
     * Exposes the latest user status so the UI can observe it
     */
    public LiveData<RegisterUserResponse> getUser() {
        return userLiveData;
    }
}
