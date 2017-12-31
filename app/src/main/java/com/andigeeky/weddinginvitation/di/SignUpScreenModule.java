package com.andigeeky.weddinginvitation.di;

import android.arch.lifecycle.ViewModelProviders;

import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.repository.RemoteRepository;
import com.andigeeky.weddinginvitation.presentation.UserViewModel;
import com.andigeeky.weddinginvitation.presentation.UserViewModelFactory;
import com.andigeeky.weddinginvitation.view.SignUpScreen;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class SignUpScreenModule {
    @Provides
    UserViewModelFactory provideUserViewModelFactory(RegisterUseCase registerUseCase) {
        return new UserViewModelFactory(registerUseCase);
    }

    @Provides
    RegisterUseCase provideRegisterUserCase(RemoteRepository remoteRepository) {
        return new RegisterUseCase(remoteRepository);
    }

    @Provides
    GoogleLoginHelper provideGoogleLoginHelper(SignUpScreen activity) {
        return new GoogleLoginHelper(activity);
    }

    @Provides
    FacebookLoginHelper provideFacebookLoginHelper(SignUpScreen activity) {
        return new FacebookLoginHelper(activity);
    }

    @Provides
    UserViewModel provideUserViewModel(SignUpScreen activity, UserViewModelFactory userViewModelFactory) {
        return ViewModelProviders.of(activity, userViewModelFactory).get(UserViewModel.class);
    }
}
