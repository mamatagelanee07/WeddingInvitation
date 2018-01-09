package com.andigeeky.weddinginvitation.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.andigeeky.weddinginvitation.model.WeddingViewModelFactory;
import com.andigeeky.weddinginvitation.presentation.SignViewModel;
import com.andigeeky.weddinginvitation.storage.upload.ImageViewModel;
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
    @ViewModelKey(SignViewModel.class)
    abstract ViewModel bindSignInViewModel(SignViewModel signViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel.class)
    abstract ViewModel bindOmageViewModel(ImageViewModel imageViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WeddingViewModelFactory factory);
}
