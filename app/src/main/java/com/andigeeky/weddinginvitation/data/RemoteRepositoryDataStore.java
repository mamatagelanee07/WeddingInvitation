package com.andigeeky.weddinginvitation.data;

import com.andigeeky.weddinginvitation.domain.RemoteRepository;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.jobs.JobManagerFactory;
import com.andigeeky.weddinginvitation.domain.service.jobs.RegisterUserJob;

import io.reactivex.Completable;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public Completable register(RegisterUserRequest request) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new RegisterUserJob(request)));
    }
}
