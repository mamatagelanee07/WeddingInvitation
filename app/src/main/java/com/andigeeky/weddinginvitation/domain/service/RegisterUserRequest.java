package com.andigeeky.weddinginvitation.domain.service;

import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.google.firebase.auth.AuthCredential;

public class RegisterUserRequest {
    private Credentials credentials;
    private AuthCredential authCredential;

    @AccountType.IAccountType
    private int accountType;

    public Credentials getCredentials() {
        return credentials;
    }

    public AuthCredential getAuthCredential() {
        return authCredential;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setAuthCredential(AuthCredential authCredential) {
        this.authCredential = authCredential;
    }

    public void setAccountType(@AccountType.IAccountType int accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "credentials=" + credentials +
                ", authCredential=" + authCredential +
                ", accountType=" + accountType +
                '}';
    }
}
