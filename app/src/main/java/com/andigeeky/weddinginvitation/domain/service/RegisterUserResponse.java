package com.andigeeky.weddinginvitation.domain.service;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUserResponse {
    public RegisterResponseEventType eventType;
    public FirebaseUser user = null;
    public FirebaseException exception = null;

    public RegisterUserResponse(RegisterResponseEventType eventType, FirebaseUser user) {
        this.eventType = eventType;
        this.user = user;
    }

    public RegisterUserResponse(RegisterResponseEventType eventType, FirebaseException exception) {
        this.eventType = eventType;
        this.exception = exception;
    }
}
