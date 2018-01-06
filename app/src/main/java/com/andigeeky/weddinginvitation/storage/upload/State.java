package com.andigeeky.weddinginvitation.storage.upload;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class State {
    public static final int UPLOADING = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;

    // Declare the @IntDef for these constants
    @IntDef({UPLOADING, SUCCESS, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IState {
    }
}
