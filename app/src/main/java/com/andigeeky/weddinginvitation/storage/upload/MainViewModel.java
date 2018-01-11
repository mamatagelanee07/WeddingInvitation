package com.andigeeky.weddinginvitation.storage.upload;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private ImageRepository imageRepository;
    private MediatorLiveData<UploadImageResponse> uploadImages = new MediatorLiveData<>();
    private MediatorLiveData<Resource<Void>> addImages = new MediatorLiveData<>();
    private MediatorLiveData<Resource<QuerySnapshot>> images = new MediatorLiveData<>();
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private int currentImageIndex = 0;

    @Inject
    MainViewModel(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImages(ArrayList<Image> imageList) {
        imageArrayList.addAll(imageList);
        startUpload();
    }

    public void addImageData(ArrayList<Image> images) {
        LiveData<Resource<Void>> addImagesData = imageRepository.addImagesData(ImageUtils.getImageData(images));
        this.addImages.addSource(addImagesData, voidResource -> addImages.setValue(voidResource));
    }

    public void getImageData() {
        LiveData<Resource<QuerySnapshot>> imagesData = imageRepository.getImagesData();
        this.images.addSource(imagesData, querySnapshotResource -> images.setValue(querySnapshotResource));
    }

    public LiveData<UploadImageResponse> getUploadImages() {
        return uploadImages;
    }

    public LiveData<Resource<Void>> getAddImages() {
        return addImages;
    }

    public LiveData<Resource<QuerySnapshot>> getImages() {
        return images;
    }

    private void startUpload() {
        LiveData<Resource<UploadTask.TaskSnapshot>> resourceLiveData = imageRepository.uploadImages(imageArrayList.get(currentImageIndex).getAbsolutePath());

        this.uploadImages.addSource(resourceLiveData, taskSnapshotResource -> {
            if ((taskSnapshotResource != null ? taskSnapshotResource.status : null) == Status.SUCCESS) {
                imageArrayList.get(currentImageIndex).setState(State.SUCCESS);
                imageArrayList.get(currentImageIndex).setDownloadURL(taskSnapshotResource.data == null ? null : taskSnapshotResource.data.getDownloadUrl().toString());
            } else if ((taskSnapshotResource != null ? taskSnapshotResource.status : null) == Status.ERROR) {
                imageArrayList.get(currentImageIndex).setState(State.ERROR);
            }

            UploadImageResponse uploadImageResponse = new UploadImageResponse();
            uploadImageResponse.setCurrentImageIndex(currentImageIndex);
            uploadImageResponse.setResult(taskSnapshotResource);
            uploadImageResponse.setImage(imageArrayList.get(currentImageIndex));
            uploadImages.setValue(uploadImageResponse);


            if ((taskSnapshotResource != null ? taskSnapshotResource.status : null) != Status.LOADING) {
                currentImageIndex++;
                if (currentImageIndex < imageArrayList.size()) {
                    uploadImages.removeSource(resourceLiveData);
                    startUpload();
                }
            }
        });
    }


    public ArrayList<Image> getSuccessfullyUploadedImages() {
        ArrayList<Image> successImages = new ArrayList<>();
        for (Image image : imageArrayList) {
            if (image.getState() == State.SUCCESS)
                successImages.add(image);
        }
        return successImages;
    }

    public ArrayList<Image> getFailedUploadedImages() {
        ArrayList<Image> errorImages = new ArrayList<>();
        for (Image image : imageArrayList) {
            if (image.getState() == State.ERROR)
                errorImages.add(image);
        }
        return errorImages;
    }

    public void clearData() {
        imageArrayList.clear();
        currentImageIndex = 0;
    }
}
