package com.andigeeky.weddinginvitation.common.utility;

import com.andigeeky.weddinginvitation.domain.service.RegisterCredentials;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.google.firebase.auth.AuthCredential;

public class RegisterRequestMapper {
    public static RegisterCredentials registerWithEmailAndPassword(Credentials credentials) {
        RegisterCredentials registerCredentials = new RegisterCredentials();
        registerCredentials.setAccountType(AccountType.PASSWORD);
        registerCredentials.setCredentials(credentials);
        return registerCredentials;
    }

    public static RegisterCredentials registerWithGoogle(AuthCredential authCredential) {
        RegisterCredentials registerCredentials = new RegisterCredentials();
        registerCredentials.setAccountType(AccountType.GOOGLE);
        registerCredentials.setAuthCredential(authCredential);
        return registerCredentials;
    }

    public static RegisterCredentials registerWithFacebook(AuthCredential authCredential) {
        RegisterCredentials registerCredentials = new RegisterCredentials();
        registerCredentials.setAccountType(AccountType.FACEBOOK);
        registerCredentials.setAuthCredential(authCredential);
        return registerCredentials;
    }
}
