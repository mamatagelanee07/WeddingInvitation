package com.andigeeky.weddinginvitation.storage.upload;

import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.google.firebase.storage.UploadTask;

public class UploadImageResponse {
    private int currentImageIndex;
    private Resource<UploadTask.TaskSnapshot> result;
    private Image image;

    public int getCurrentImageIndex() {
        return currentImageIndex;
    }

    public void setCurrentImageIndex(int currentImageIndex) {
        this.currentImageIndex = currentImageIndex;
    }

    public Resource<UploadTask.TaskSnapshot> getResult() {
        return result;
    }

    public void setResult(Resource<UploadTask.TaskSnapshot> result) {
        this.result = result;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
