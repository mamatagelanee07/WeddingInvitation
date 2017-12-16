package com.andigeeky.weddinginvitation.newarchitecture;

import com.andigeeky.weddinginvitation.auth.vo.User;

import io.reactivex.Completable;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public Completable register(User user) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new RegisterUserJob(user)));
    }
}
