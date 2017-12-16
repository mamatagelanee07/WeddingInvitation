package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class RegisterUserRxBus {

    private static RegisterUserRxBus instance;
    private final PublishRelay<RegisterUserResponse> relay;

    public static synchronized RegisterUserRxBus getInstance() {
        if (instance == null) {
            instance = new RegisterUserRxBus();
        }
        return instance;
    }

    private RegisterUserRxBus() {
        relay = PublishRelay.create();
    }

    public void post(RegisterResponseEventType eventType, User user) {
        relay.accept(new RegisterUserResponse(eventType, user));
    }

    public Observable<RegisterUserResponse> toObservable() {
        return relay;
    }
}
