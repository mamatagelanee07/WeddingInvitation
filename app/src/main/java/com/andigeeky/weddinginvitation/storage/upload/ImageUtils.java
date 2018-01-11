package com.andigeeky.weddinginvitation.storage.upload;

import com.andigeeky.weddinginvitation.firestore.ImageVO;

import java.util.ArrayList;
import java.util.UUID;

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

    public static ArrayList<ImageVO> getImageData(ArrayList<Image> imageList) {
        ArrayList<ImageVO> imageVOS = new ArrayList<>();
        for (Image image : imageList) {
            ImageVO imageVO = new ImageVO();
            imageVO.setId(UUID.randomUUID().toString());
            imageVO.setName(image.getFileName());
            imageVO.setUrl(image.getDownloadURL());
            imageVOS.add(imageVO);
        }
        return imageVOS;
    }
}
