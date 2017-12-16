package com.andigeeky.weddinginvitation.domain.service;

import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.model.User;
import com.google.firebase.auth.AuthCredential;

import java.io.Serializable;

public class RegisterUserRequest implements Serializable{
    public User user;
    public AuthCredential authCredential;

    @AccountType.IAccountType
    public int accountType;

    public User getUser() {
        return user;
    }

    public AuthCredential getAuthCredential() {
        return authCredential;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setUser(User user) {
        this.user = user;
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
                "user=" + user +
                ", authCredential=" + authCredential +
                ", accountType=" + accountType +
                '}';
    }
}
