package com.andigeeky.weddinginvitation.auth.contract;

import com.andigeeky.weddinginvitation.auth.AccountType;
import com.google.firebase.auth.FirebaseUser;

public interface IRegisterUser {
    FirebaseUser registerUser(AccountType accountType);
}
