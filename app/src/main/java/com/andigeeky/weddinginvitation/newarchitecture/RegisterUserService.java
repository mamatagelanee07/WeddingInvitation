package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

    public void registerUser(User user) throws IOException, RemoteException {
        Task<AuthResult> resultTask = firebaseAuth.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword());

        if (!resultTask.isSuccessful() || resultTask.getResult().getUser() == null) {
            throw new RemoteException();
        }

        Timber.d("successful remote response: " + resultTask.getResult().getUser());
    }
}