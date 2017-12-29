package com.andigeeky.weddinginvitation.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.temp.Resource;
import com.google.firebase.auth.AuthResult;

public class UserViewModel extends ViewModel {
    private RegisterUseCase registerUseCase;
    private MediatorLiveData<Resource<AuthResult>> mediatorLiveData = new MediatorLiveData<>();

    UserViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void registerUser(RegisterUserRequest request) {
        LiveData<Resource<AuthResult>> resourceLiveData = registerUseCase.registerUser(request);
        this.mediatorLiveData.addSource(resourceLiveData, new Observer<Resource<AuthResult>>() {
            @Override
            public void onChanged(@Nullable Resource<AuthResult> authResultResource) {
                mediatorLiveData.setValue(authResultResource);
            }
        });
    }

    /**
     * Exposes the latest user status so the UI can observe it
     */
    public LiveData<Resource<AuthResult>> getUser() {
        return mediatorLiveData;
    }
}
