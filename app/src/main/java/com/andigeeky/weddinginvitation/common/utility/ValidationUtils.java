package com.andigeeky.weddinginvitation.common.utility;


import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    public static boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * Password should be at least 6 character long
     *
     * @param password password
     * @return true if password is valid
     */
    public static boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() > 6;
    }
}
