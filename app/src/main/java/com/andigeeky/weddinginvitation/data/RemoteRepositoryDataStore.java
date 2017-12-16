package com.andigeeky.weddinginvitation.data;

import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.domain.service.jobs.JobManagerFactory;
import com.andigeeky.weddinginvitation.domain.service.jobs.RegisterUserJob;
import com.andigeeky.weddinginvitation.domain.RemoteRepository;

import io.reactivex.Completable;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public Completable register(User user) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new RegisterUserJob(user)));
    }
}
