package com.andigeeky.weddinginvitation.domain.service;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RemoteException extends Exception {

    public RemoteException(@IError String message) {
        super(message);
    }

    public static final String ERROR_FIREBASE_EMAIL_AUTH = "email is not valid";
    public static final String ERROR_FIREBASE_USER_EXISTS_ALREADY = "user exists already";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ERROR_FIREBASE_EMAIL_AUTH, ERROR_FIREBASE_USER_EXISTS_ALREADY})
    public @interface IError {
    }
}
