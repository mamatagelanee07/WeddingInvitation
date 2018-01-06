package com.andigeeky.weddinginvitation.storage.upload;

import java.util.ArrayList;

public class ImageUtils {
    public static ArrayList<Image> getImages(ArrayList<String> imagePaths) {
        ArrayList<Image> images = new ArrayList<>();
        for (String imagePath : imagePaths) {
            Image image = new Image();
            image.setFilePath(imagePath);
            images.add(image);
        }
        return images;
    }
}
