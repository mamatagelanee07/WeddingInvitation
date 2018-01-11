package com.andigeeky.weddinginvitation.common;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import javax.inject.Inject;

import timber.log.Timber;

public class FacebookUtils {
    private CallbackManager callbackManager;
    private Activity mActivity;
    private FacebookAuthHandler authHandler;

    public FacebookUtils(Activity mActivity) {
        this.mActivity = mActivity;
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("facebook:onSuccess:" + loginResult);
                if (authHandler != null)
                    authHandler.onComplete(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Timber.d("facebook:onCancel");
                if (authHandler != null)
                    authHandler.onError(null);
            }

            @Override
            public void onError(FacebookException error) {
                Timber.d("facebook:onError", error);
                if (authHandler != null)
                    authHandler.onError(error);
            }
        });

    }

    public void registerHandler(FacebookAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    public void unregisterHandler() {
        this.authHandler = null;
    }

    public void getFacebookCredentials() {
        LoginManager.getInstance().logInWithReadPermissions(mActivity,
                Arrays.asList("public_profile", "email"));
    }

    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface FacebookAuthHandler {
        void onComplete(String facebookCredentials);

        void onError(FacebookException e);
    }
}
