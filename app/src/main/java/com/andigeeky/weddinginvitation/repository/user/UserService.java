package com.andigeeky.weddinginvitation.repository.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.andigeeky.weddinginvitation.domain.service.RegisterCredentials;
import com.andigeeky.weddinginvitation.domain.service.networking.common.InstantAppExecutors;
import com.andigeeky.weddinginvitation.domain.service.networking.common.NetworkBoundResource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserService {
    private static UserService instance;
    private final FirebaseAuth firebaseAuth;

    public UserService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public FirebaseUser getUser() {
        return firebaseAuth.getCurrentUser();
    }

    public LiveData<Resource<AuthResult>> registerUser(RegisterCredentials request) {
        return new NetworkBoundResource<AuthResult>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<AuthResult>> createCall() {
                return getRegisterUserTask(request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> updateUser(UserProfileChangeRequest request) {
        return new NetworkBoundResource<Void>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<Void>> createCall() {
                return getUpdateUserTask(request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> updatePassword(String password) {
        return new NetworkBoundResource<Void>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<Void>> createCall() {
                return getUpdatePasswordTask(password);
            }
        }.asLiveData();
    }

    private LiveData<Task<AuthResult>> getRegisterUserTask(RegisterCredentials request) {
        MutableLiveData<Task<AuthResult>> liveTask = new MutableLiveData<>();
        Task<AuthResult> resultTask = null;
        switch (request.getAccountType()) {
            case AccountType.PASSWORD:
                resultTask = firebaseAuth.createUserWithEmailAndPassword(
                        request.getCredentials().getEmail(), request.getCredentials().getPassword());
                break;
            case AccountType.GOOGLE:
            case AccountType.FACEBOOK:
                resultTask = firebaseAuth.signInWithCredential(request.getAuthCredential());
                break;
        }

        if (resultTask != null) {
            resultTask.addOnCompleteListener(liveTask::postValue);
        }

        return liveTask;
    }

    private LiveData<Task<Void>> getUpdatePasswordTask(String password) {
        MutableLiveData<Task<Void>> liveTask = new MutableLiveData<>();
        Task<Void> voidTask = firebaseAuth.getCurrentUser().updatePassword(password);
        voidTask.addOnCompleteListener(liveTask::postValue);
        return liveTask;
    }

    private LiveData<Task<Void>> getUpdateUserTask(UserProfileChangeRequest userProfileChangeRequest) {
        MutableLiveData<Task<Void>> liveTask = new MutableLiveData<>();
        Task<Void> voidTask = firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest);
        voidTask.addOnCompleteListener(liveTask::postValue);
        return liveTask;
    }

}