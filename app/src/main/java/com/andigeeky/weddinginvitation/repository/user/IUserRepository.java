package com.andigeeky.weddinginvitation.repository.user;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public interface IUserRepository {
    FirebaseUser getUser();

    LiveData<Resource<Void>> updateUser(UserProfileChangeRequest userProfileChangeRequest);

    LiveData<Resource<Void>> updatePassword(String password);
}
