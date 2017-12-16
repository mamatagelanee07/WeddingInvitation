package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;

import io.reactivex.Completable;

public interface RemoteRepository {
    Completable register(User user);
}
