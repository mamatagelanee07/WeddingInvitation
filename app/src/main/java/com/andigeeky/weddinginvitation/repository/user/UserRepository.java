package com.andigeeky.weddinginvitation.repository.user;


import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterCredentials;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    @Inject
    public UserRepository() {
    }

    public LiveData<Resource<AuthResult>> register(RegisterCredentials registerCredentials) {
        return UserService.getInstance().registerUser(registerCredentials);
    }

    public LiveData<Resource<AuthResult>> login(Credentials credential) {
        return UserService.getInstance().login(credential);
    }

    public LiveData<Resource<Void>> resetPassword(Credentials credential) {
        return UserService.getInstance().resetPassword(credential);
    }

    public FirebaseUser getUser() {
        return UserService.getInstance().getUser();
    }


    public LiveData<Resource<Void>> updateUser(UserProfileChangeRequest userProfileChangeRequest) {
        return UserService.getInstance().updateUser(userProfileChangeRequest);
    }

    public LiveData<Resource<Void>> updatePassword(String password) {
        return UserService.getInstance().updatePassword(password);
    }
}
