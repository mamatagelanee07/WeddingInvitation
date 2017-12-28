package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.andigeeky.weddinginvitation.domain.RegisterUseCase;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private final RegisterUseCase registerUseCase;

    public UserViewModelFactory(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(registerUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
