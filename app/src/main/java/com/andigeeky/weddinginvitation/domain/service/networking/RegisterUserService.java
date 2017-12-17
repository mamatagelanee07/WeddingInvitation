package com.andigeeky.weddinginvitation.domain.service.networking;

import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.domain.service.RemoteException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import timber.log.Timber;

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

    public FirebaseUser registerUser(RegisterUserRequest request) throws IOException, RemoteException {

        Task<AuthResult> resultTask;

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
                throw new RemoteException();
        }


        if (!resultTask.isSuccessful() || resultTask.getResult().getUser() == null) {
            throw new RemoteException();
        }

        Timber.d("successful remote response: " + resultTask.getResult().getUser());
        return resultTask.getResult().getUser();
    }
}