package com.andigeeky.weddinginvitation.domain.service;

import com.andigeeky.weddinginvitation.model.User;

public class RegisterUserResponse {
    public final RegisterResponseEventType eventType;
    public final User user;

    public RegisterUserResponse(RegisterResponseEventType eventType, User user) {
        this.eventType = eventType;
        this.user = user;
    }
}
