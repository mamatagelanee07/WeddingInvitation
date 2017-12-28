package com.andigeeky.weddinginvitation.di;

import android.content.Context;

import com.andigeeky.weddinginvitation.WeddingApplication;
import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.RemoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module(subcomponents = {SignUpScreenComponent.class})
public class AppModule {
    @Singleton
    @Provides
    Context provideContext(WeddingApplication application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    RemoteRepository provideRemoteRepository() {
        return new RemoteRepositoryDataStore();
    }
}
