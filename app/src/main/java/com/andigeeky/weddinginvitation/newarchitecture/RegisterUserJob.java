package com.andigeeky.weddinginvitation.newarchitecture;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.auth.vo.User;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import timber.log.Timber;


public class RegisterUserJob extends Job {
    private static final String TAG = RegisterUserJob.class.getCanonicalName();
    private final User user;

    public RegisterUserJob(User user) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());
        this.user = user;
    }

    @Override
    public void onAdded() {
        Timber.d("Executing onAdded() for user " + user);
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("Executing onRun() for user " + user);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        RegisterUserService.getInstance().registerUser(user);

        // remote call was successful--the Comment will be updated locally to reflect that sync is no longer pending
        RegisterUserRxBus.getInstance().post(RegisterResponseEventType.SUCCESS, user);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        RegisterUserRxBus.getInstance().post(RegisterResponseEventType.FAILED, user);
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

