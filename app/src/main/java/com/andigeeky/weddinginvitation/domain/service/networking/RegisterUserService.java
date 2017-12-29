package com.andigeeky.weddinginvitation.domain.service.networking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.temp.InstantAppExecutors;
import com.andigeeky.weddinginvitation.temp.NetworkBoundResource;
import com.andigeeky.weddinginvitation.temp.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserService {
    private static RegisterUserService instance;
    private final FirebaseAuth firebaseAuth;

    public RegisterUserService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    public static synchronized RegisterUserService getInstance() {
        if (instance == null) {
            instance = new RegisterUserService();
        }
        return instance;
    }

    public LiveData<Resource<AuthResult>> registerUser(RegisterUserRequest request) {
        return new NetworkBoundResource<AuthResult>(new InstantAppExecutors()) {
            @NonNull
            @Override
            protected LiveData<Task<AuthResult>> createCall() {
                return getAuthResultTask(request);
            }
        }.asLiveData();
    }

    private LiveData<Task<AuthResult>> getAuthResultTask(RegisterUserRequest request) {
        MutableLiveData<Task<AuthResult>> liveTask = new MutableLiveData<>();
        Task<AuthResult> resultTask = null;
        switch (request.getAccountType()) {
            case AccountType.PASSWORD:
                resultTask = firebaseAuth.createUserWithEmailAndPassword(
                        request.getUser().getEmail(), request.getUser().getPassword());
                break;
            case AccountType.GOOGLE:
            case AccountType.FACEBOOK:
                resultTask = firebaseAuth.signInWithCredential(request.getAuthCredential());
                break;
            default:
                break;
        }

        resultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                liveTask.postValue(task);
            }
        });

        return liveTask;
    }
}