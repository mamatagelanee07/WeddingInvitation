package com.andigeeky.weddinginvitation.storage.upload;

import android.net.Uri;

public class Image {
    private String absolutePath;

    private String downloadURL;

    @State.IState
    private int state;

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getFileName() {
        return Uri.parse(getAbsolutePath()).getLastPathSegment();
    }

    public void setFilePath(String filePath) {
        this.absolutePath = filePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    @State.IState
    public int getState() {
        return state;
    }

    public void setState(@State.IState int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (state != image.state) return false;
        if (!absolutePath.equals(image.absolutePath)) return false;
        return downloadURL.equals(image.downloadURL);
    }

    @Override
    public int hashCode() {
        int result = absolutePath.hashCode();
        result = 31 * result + downloadURL.hashCode();
        result = 31 * result + state;
        return result;
    }
}
