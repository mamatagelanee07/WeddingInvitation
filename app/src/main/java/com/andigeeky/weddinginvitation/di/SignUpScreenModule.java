package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
import com.andigeeky.weddinginvitation.view.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class SignUpScreenModule {
    @Provides
    GoogleLoginHelper provideGoogleLoginHelper(LoginActivity activity) {
        return new GoogleLoginHelper(activity);
    }

    @Provides
    FacebookLoginHelper provideFacebookLoginHelper(LoginActivity activity) {
        return new FacebookLoginHelper(activity);
    }
}
