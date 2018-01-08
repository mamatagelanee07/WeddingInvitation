package com.andigeeky.weddinginvitation.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    FirebaseFirestore provideFireStore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    FirebaseUser provideFirebaseUser(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getCurrentUser();
    }
}
