package com.andigeeky.weddinginvitation.storage.upload;

import android.net.Uri;

public class Image {
    private String absolutePath;

    @State.IState
    private int state;

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
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        return getAbsolutePath() != null ? getAbsolutePath().equals(image.getAbsolutePath()) : image.getAbsolutePath() == null;
    }

    @Override
    public int hashCode() {
        return getAbsolutePath() != null ? getAbsolutePath().hashCode() : 0;
    }
}
