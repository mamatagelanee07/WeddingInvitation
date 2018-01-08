package com.andigeeky.weddinginvitation.view.user;

import android.arch.lifecycle.ViewModel;

import com.andigeeky.weddinginvitation.repository.user.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;


public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FirebaseUser getUser() {
        return userRepository.getUser();
    }
}
