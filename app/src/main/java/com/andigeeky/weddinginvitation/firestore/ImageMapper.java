package com.andigeeky.weddinginvitation.firestore;

import java.util.ArrayList;

public class ImageMapper {
    public static ImageCollection mapFrom(ArrayList<ImageVO> images) {
        ImageCollection imageCollection = new ImageCollection();
        imageCollection.setKey("image");
        imageCollection.setImages(images);
        return imageCollection;
    }
}
