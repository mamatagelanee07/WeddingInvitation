package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;

public class RegisterUserResponse {
    public final RegisterResponseEventType eventType;
    public final User user;

    public RegisterUserResponse(RegisterResponseEventType eventType, User user) {
        this.eventType = eventType;
        this.user = user;
    }
}
