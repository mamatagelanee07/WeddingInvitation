package com.andigeeky.weddinginvitation.di;

import android.content.Context;

import com.andigeeky.weddinginvitation.WeddingApplication;
import com.andigeeky.weddinginvitation.repository.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.repository.RemoteRepository;
import com.andigeeky.weddinginvitation.storage.upload.ImageRepository;
import com.andigeeky.weddinginvitation.storage.upload.ImageRepositoryDataStore;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivityComponent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module(subcomponents = {SignUpScreenComponent.class, UploadActivityComponent.class})
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

    @Singleton
    @Provides
    ImageRepository provideImageRepository() {
        return new ImageRepositoryDataStore();
    }
}
