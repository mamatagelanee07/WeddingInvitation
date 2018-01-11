package com.andigeeky.weddinginvitation.di;

import android.content.Context;

import com.andigeeky.weddinginvitation.WeddingApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module(includes = {ViewModelModule.class, MainActivityModule.class})
public class AppModule {
    @Singleton
    @Provides
    Context provideContext(WeddingApplication application) {
        return application.getApplicationContext();
    }
}
