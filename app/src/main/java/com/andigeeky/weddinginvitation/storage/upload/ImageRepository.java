package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.firestore.AddImageService;
import com.andigeeky.weddinginvitation.firestore.ImageVO;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageRepository {

    @Inject
    public ImageRepository() {
    }

    public LiveData<Resource<UploadTask.TaskSnapshot>> uploadImages(String filePath) {
        return ImageService.getInstance().uploadImage(filePath);
    }

    public LiveData<Resource<Void>> addImagesData(ArrayList<ImageVO> imageVOS) {
        return AddImageService.getInstance().addImages(imageVOS);
    }

    public LiveData<Resource<QuerySnapshot>> getImagesData() {
        return AddImageService.getInstance().getImages();
    }
}
