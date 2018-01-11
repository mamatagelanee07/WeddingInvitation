package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.andigeeky.weddinginvitation.domain.service.RegisterCredentials;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.repository.user.UserRepository;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

public class RegisterUserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MediatorLiveData<Resource<AuthResult>> registeredUser = new MediatorLiveData<>();
    private MediatorLiveData<Resource<AuthResult>> login = new MediatorLiveData<>();
    private MediatorLiveData<Resource<Void>> resetPassword = new MediatorLiveData<>();

    @Inject
    RegisterUserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterCredentials request) {
        LiveData<Resource<AuthResult>> resourceLiveData = userRepository.register(request);
        this.registeredUser.addSource(resourceLiveData, authResultResource -> registeredUser.setValue(authResultResource));
    }

    public void login(Credentials credentials) {
        LiveData<Resource<AuthResult>> resourceLiveData = userRepository.login(credentials);
        this.login.addSource(resourceLiveData, authResultResource -> login.setValue(authResultResource));
    }

    public void resetPassword(Credentials credentials) {
        LiveData<Resource<Void>> resourceLiveData = userRepository.resetPassword(credentials);
        this.resetPassword.addSource(resourceLiveData, authResultResource -> resetPassword.setValue(authResultResource));
    }

    public LiveData<Resource<AuthResult>> getRegisteredUser() {
        return registeredUser;
    }

    public LiveData<Resource<AuthResult>> getLogin() {
        return login;
    }
    public LiveData<Resource<Void>> getResetPassword() {
        return resetPassword;
    }
}
