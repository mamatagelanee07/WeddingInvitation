package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;

public class UserViewModel extends ViewModel {
    private RegisterUseCase registerUseCase;
    private MutableLiveData<RegisterUserResponse> userLiveData = new MutableLiveData<>();


    public UserViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void registerUser(RegisterUserRequest request) {
        registerUseCase.registerUser(request);
    }

    /**
     * Exposes the latest user status so the UI can observe it
     */
    public LiveData<RegisterUserResponse> getUser() {
        return userLiveData;
    }

    public void updateUser(RegisterUserResponse response) {
        this.userLiveData.postValue(response);
    }
}
