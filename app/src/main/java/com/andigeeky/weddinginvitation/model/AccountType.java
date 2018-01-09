package com.andigeeky.weddinginvitation.model;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AccountType {
    public static final int PASSWORD = 1;
    public static final int GOOGLE = 2;
    public static final int FACEBOOK = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PASSWORD, GOOGLE, FACEBOOK})
    public @interface IAccountType {
    }
}