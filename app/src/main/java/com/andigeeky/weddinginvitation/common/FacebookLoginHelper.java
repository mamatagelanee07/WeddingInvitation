package com.andigeeky.weddinginvitation.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.andigeeky.weddinginvitation.view.SignUpScreen;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import timber.log.Timber;

public class FacebookLoginHelper {
    private CallbackManager callbackManager;
    private Context context;

    public FacebookLoginHelper(Context context, Callback callback) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("facebook:onSuccess:" + loginResult);
                callback.onComplete(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Timber.d("facebook:onCancel");
                callback.onComplete(null);
            }

            @Override
            public void onError(FacebookException error) {
                Timber.d("facebook:onError", error);
                callback.onComplete(null);
            }
        });

    }

    public void getFacebookCredentials() {
        LoginManager.getInstance().logInWithReadPermissions((Activity) context,
                Arrays.asList("public_profile", "email"));
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface Callback {
        void onComplete(String facebookCredentials);
    }
}
