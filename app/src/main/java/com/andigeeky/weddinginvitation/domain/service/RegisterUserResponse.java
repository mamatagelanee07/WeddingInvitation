package com.andigeeky.weddinginvitation.domain.service;

import com.google.firebase.auth.FirebaseUser;

public class RegisterUserResponse {
    public final RegisterResponseEventType eventType;
    public final FirebaseUser user;

    public RegisterUserResponse(RegisterResponseEventType eventType, FirebaseUser user) {
        this.eventType = eventType;
        this.user = user;
    }
}
