package com.andigeeky.weddinginvitation.domain.service.jobs;

import android.support.annotation.NonNull;

import com.andigeeky.weddinginvitation.domain.service.jobs.JobManagerFactory;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

public class SchedulerJobService extends FrameworkJobSchedulerService {

    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManagerFactory.getJobManager();
    }
}
