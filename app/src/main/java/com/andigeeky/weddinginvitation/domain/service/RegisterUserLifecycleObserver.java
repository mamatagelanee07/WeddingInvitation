package com.andigeeky.weddinginvitation.domain.service;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import com.andigeeky.weddinginvitation.presentation.UserViewModel;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Observes sign up screen lifecycle
 */
public class RegisterUserLifecycleObserver implements LifecycleObserver {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private UserViewModel userViewModel;

    public RegisterUserLifecycleObserver(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume lifecycle event.");
        disposables.add(RegisterUserRxBus.getInstance().toObservable()
                .subscribe(this::handleRegisterResponse, t -> Timber.e(t, "error handling sync response")));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.d("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleRegisterResponse(RegisterUserResponse response) {
        userViewModel.updateUser(response);
    }
}
