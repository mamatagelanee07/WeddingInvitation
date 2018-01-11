package com.andigeeky.weddinginvitation.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.andigeeky.weddinginvitation.model.WeddingViewModelFactory;
import com.andigeeky.weddinginvitation.presentation.RegisterUserViewModel;
import com.andigeeky.weddinginvitation.storage.upload.MainViewModel;
import com.andigeeky.weddinginvitation.view.user.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterUserViewModel.class)
    abstract ViewModel bindRegisterUserViewModel(RegisterUserViewModel registerUserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WeddingViewModelFactory factory);
}
