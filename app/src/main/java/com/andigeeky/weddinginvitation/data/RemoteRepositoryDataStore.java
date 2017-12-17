package com.andigeeky.weddinginvitation.data;

import com.andigeeky.weddinginvitation.domain.RemoteRepository;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.jobs.JobManagerFactory;
import com.andigeeky.weddinginvitation.domain.service.jobs.RegisterUserJob;

public class RemoteRepositoryDataStore implements RemoteRepository {
    @Override
    public void register(RegisterUserRequest request) {
        JobManagerFactory.getJobManager().addJobInBackground(new RegisterUserJob(request));
    }
}
