package com.andigeeky.weddinginvitation.storage.upload;

import android.net.Uri;

public class Image {
    private String fileName;
    private String absolutePath;

    public String getFileName() {
        return Uri.parse(absolutePath).getLastPathSegment();
    }

    public void setFilePath(String filePath) {
        this.absolutePath = filePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
