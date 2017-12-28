package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.WeddingApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        /* Use AndroidInjectionModule.class if you're not using support library */
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(WeddingApplication application);

        AppComponent build();
    }

    void inject(WeddingApplication app);
}
