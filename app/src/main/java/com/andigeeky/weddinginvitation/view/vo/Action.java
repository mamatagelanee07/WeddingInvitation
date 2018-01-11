package com.andigeeky.weddinginvitation.view.vo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Action {
    public static final int ACTION_LOGIN = 1;
    public static final int ACTION_REGISTER = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION_REGISTER, ACTION_LOGIN})
    public @interface IAction {
    }
}
