package com.andigeeky.weddinginvitation.storage.upload;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageUtils {
    public static HashMap<String, Image> getImages(ArrayList<String> imagePaths) {
        HashMap<String, Image> imageHashMap = new HashMap<>();
        for (String imagePath : imagePaths) {
            Image image = new Image();
            image.setFilePath(imagePath);
            imageHashMap.put(image.getFileName(), image);
        }
        return imageHashMap;
    }
}
