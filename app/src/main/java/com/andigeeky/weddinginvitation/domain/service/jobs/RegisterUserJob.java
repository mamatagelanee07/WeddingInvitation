package com.andigeeky.weddinginvitation.domain.service.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.service.RegisterResponseEventType;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRxBus;
import com.andigeeky.weddinginvitation.domain.service.networking.RegisterUserService;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;


public class RegisterUserJob extends Job {
    private static final String TAG = RegisterUserJob.class.getCanonicalName();
    private RegisterUserRequest request;

    public RegisterUserJob(RegisterUserRequest request) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG));
        this.request = request;
    }

    @Override
    public void onAdded() {
        Timber.d("Executing onAdded() for user " + request);
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("Executing onRun() for user " + request);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        FirebaseUser firebaseUser = RegisterUserService.getInstance().registerUser(request);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        RegisterUserRxBus.getInstance().post(RegisterResponseEventType.SUCCESS, firebaseUser);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        RegisterUserRxBus.getInstance().post(RegisterResponseEventType.FAILED, null);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        /*if (throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;*/
        return RetryConstraint.CANCEL;
    }
}

