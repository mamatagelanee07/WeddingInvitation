package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.andigeeky.weddinginvitation.domain.service.RegisterCredentials;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.repository.user.UserRepository;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

public class RegisterUserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MediatorLiveData<Resource<AuthResult>> mediatorLiveData = new MediatorLiveData<>();

    @Inject
    RegisterUserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterCredentials request) {
        LiveData<Resource<AuthResult>> resourceLiveData = userRepository.register(request);
        this.mediatorLiveData.addSource(resourceLiveData, authResultResource -> mediatorLiveData.setValue(authResultResource));
    }

    public LiveData<Resource<AuthResult>> getUser() {
        return mediatorLiveData;
    }
}
