package com.andigeeky.weddinginvitation.auth.implementation;

import com.andigeeky.weddinginvitation.auth.AccountType;
import com.andigeeky.weddinginvitation.auth.contract.IRegisterUser;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mamata on 12/15/2017.
 */

public class RegisterUser implements IRegisterUser {
    @Override
    public FirebaseUser registerUser(AccountType accountType) {
        switch (accountType) {
            case EMAIL:
                break;
            case GOOGLE:
                break;
            case FACEBOOK:
                break;
            default:
                break;
        }
        return null;
    }
}
