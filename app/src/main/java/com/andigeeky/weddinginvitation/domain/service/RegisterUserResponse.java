package com.andigeeky.weddinginvitation.domain.service;

import android.support.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUserResponse {
    @NonNull
    private RegisterResponseEventType eventType;
    private FirebaseUser user = null;
    private FirebaseException exception = null;

    public RegisterUserResponse(@NonNull RegisterResponseEventType eventType, FirebaseUser user) {
        this.eventType = eventType;
        this.user = user;
    }

    public RegisterUserResponse(@NonNull RegisterResponseEventType eventType, FirebaseException exception) {
        this.eventType = eventType;
        this.exception = exception;
    }

    @NonNull
    public RegisterResponseEventType getEventType() {
        return eventType;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public FirebaseException getException() {
        return exception;
    }
}
