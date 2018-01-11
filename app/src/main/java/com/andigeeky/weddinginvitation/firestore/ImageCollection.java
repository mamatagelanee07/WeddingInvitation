package com.andigeeky.weddinginvitation.firestore;

import java.util.ArrayList;

public class ImageCollection {
    private String key;
    private ArrayList<ImageVO> images;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<ImageVO> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageVO> images) {
        this.images = images;
    }
}
