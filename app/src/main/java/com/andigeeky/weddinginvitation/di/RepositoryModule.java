package com.andigeeky.weddinginvitation.di;

import com.andigeeky.weddinginvitation.repository.RemoteRepository;
import com.andigeeky.weddinginvitation.repository.user.UserRepository;
import com.andigeeky.weddinginvitation.storage.upload.ImageRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {
    @Binds
    abstract RemoteRepository bindRemoteRepository(RemoteRepository remoteRepository);

    @Binds
    abstract UserRepository bindUserRepository(UserRepository iUserRepository);

    @Binds
    abstract ImageRepository bindImageRepository(ImageRepository imageRepository);
}
