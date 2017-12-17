package com.andigeeky.weddinginvitation.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.andigeeky.weddinginvitation.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import timber.log.Timber;

public class GoogleLoginHelper {
    public static final int RC_SIGN_IN = 9001;

    private Context context;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleLoginHelper(Context context) {
        this.context = context;
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void getGoogleCredentials() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        ((Activity) context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public GoogleSignInAccount onLoginResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                return task.getResult(ApiException.class);
            } catch (ApiException e) {
                Timber.d("Google sign in failed", e);
            }
        }
        return null;
    }
}
