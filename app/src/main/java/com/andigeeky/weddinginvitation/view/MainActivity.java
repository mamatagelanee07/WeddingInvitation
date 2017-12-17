package com.andigeeky.weddinginvitation.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterResponseEventType;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserLifecycleObserver;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.presentation.UserViewModel;
import com.andigeeky.weddinginvitation.presentation.UserViewModelFactory;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private UserViewModel viewModel;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RemoteRepositoryDataStore remoteRepositoryDataStore = new RemoteRepositoryDataStore();
        RegisterUseCase registerUseCase = new RegisterUseCase(remoteRepositoryDataStore);
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(registerUseCase);
        viewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        RegisterUserLifecycleObserver registerUserLifecycleObserver = new RegisterUserLifecycleObserver(viewModel);
        getLifecycle().addObserver(registerUserLifecycleObserver);


        findViewById(R.id.btn_register).setOnClickListener(view -> {
            registerWithEmailAndPassword(viewModel);
        });

        findViewById(R.id.btn_google).setOnClickListener(view -> {
            getGoogleCredentials();
        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile", "email"));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                registerWithFacebook(FacebookAuthProvider
                        .getCredential(loginResult.getAccessToken().getToken()));
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        viewModel.getUser().observe(this, registerUserResponse -> {
            if (registerUserResponse.getEventType() == RegisterResponseEventType.SUCCESS) {
                Toast.makeText(MainActivity.this, "User registered: "
                        + registerUserResponse.getEventType() + " : "
                        + registerUserResponse.getUser().getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "User registered: "
                        + registerUserResponse.getEventType() + " : "
                        + registerUserResponse.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGoogleCredentials() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                registerWithGoogle(viewModel, account);
            } catch (ApiException e) {
                Timber.d("Google sign in failed", e);
            }
        }
    }

    private void registerWithEmailAndPassword(UserViewModel viewModel) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        User user = new User();
        user.setEmail("gelaneeem123@gmail.com");
        user.setPassword("password");
        registerUserRequest.setAccountType(AccountType.PASSWORD);
        registerUserRequest.setUser(user);
        viewModel.registerUser(registerUserRequest);
    }

    private void registerWithGoogle(UserViewModel viewModel, GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setAccountType(AccountType.GOOGLE);
        registerUserRequest.setAuthCredential(credential);
        viewModel.registerUser(registerUserRequest);
    }

    private void registerWithFacebook(AuthCredential authCredential) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setAccountType(AccountType.FACEBOOK);
        registerUserRequest.setAuthCredential(authCredential);
        viewModel.registerUser(registerUserRequest);
    }
}
