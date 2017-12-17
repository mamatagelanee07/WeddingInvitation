package com.andigeeky.weddinginvitation.domain.service;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseUser;
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

    public void post(RegisterResponseEventType eventType, FirebaseUser user) {
        relay.accept(new RegisterUserResponse(eventType, user));
    }

    public void post(RegisterResponseEventType eventType, FirebaseException exception) {
        relay.accept(new RegisterUserResponse(eventType, exception));
    }

    public Observable<RegisterUserResponse> toObservable() {
        return relay;
    }
}
