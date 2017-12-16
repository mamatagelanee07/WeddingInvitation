package com.andigeeky.weddinginvitation.domain;

import com.andigeeky.weddinginvitation.model.User;

import io.reactivex.Completable;

public interface RemoteRepository {
    Completable register(User user);
}
