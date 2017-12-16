package com.andigeeky.weddinginvitation.newarchitecture;

import android.arch.lifecycle.ViewModel;

import com.andigeeky.weddinginvitation.auth.vo.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private RegisterUseCase registerUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public UserViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void registerUser(User user) {
        disposables.add(registerUseCase.registerUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("add comment success"),
                        t -> Timber.e(t, "add comment error")));
    }
}
