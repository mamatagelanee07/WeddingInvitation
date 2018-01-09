package com.andigeeky.weddinginvitation.repository;

import com.andigeeky.weddinginvitation.repository.user.IUserRepository;
import com.andigeeky.weddinginvitation.storage.upload.IImageRepository;

public interface IRemoteRepository {

    IUserRepository getUserRepository();

    IImageRepository getImageRepository();
}
