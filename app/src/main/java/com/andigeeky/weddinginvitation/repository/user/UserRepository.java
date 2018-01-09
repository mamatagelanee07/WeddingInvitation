package com.andigeeky.weddinginvitation.repository.user;


import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository implements IUserRepository {

    @Inject
    public UserRepository() {
    }

    @Override
    public FirebaseUser getUser() {
        return UserService.getInstance().getUser();
    }

    @Override
    public LiveData<Resource<Void>> updateUser(UserProfileChangeRequest userProfileChangeRequest) {
        return UserService.getInstance().updateUser(userProfileChangeRequest);
    }

    @Override
    public LiveData<Resource<Void>> updatePassword(String password) {
        return UserService.getInstance().updatePassword(password);
    }
}
