package com.andigeeky.weddinginvitation.domain.service.networking;

import android.arch.lifecycle.MutableLiveData;

import com.andigeeky.weddinginvitation.domain.service.RegisterResponseEventType;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
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

    public MutableLiveData<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        Task<AuthResult> resultTask = getAuthResultTask(request);
        MutableLiveData<RegisterUserResponse> response = new MutableLiveData<>();

        resultTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                response.postValue(new RegisterUserResponse(RegisterResponseEventType.SUCCESS,
                        task.getResult().getUser()));
            } else {
                response.postValue(new RegisterUserResponse(RegisterResponseEventType.FAILED,
                        (FirebaseException) task.getException()));
            }
        });
        return response;
    }

    private Task<AuthResult> getAuthResultTask(RegisterUserRequest request) {
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
        return resultTask;
    }
}