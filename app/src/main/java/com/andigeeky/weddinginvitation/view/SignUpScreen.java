package com.andigeeky.weddinginvitation.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class SignUpScreen extends AppCompatActivity {
    private static final String TAG = SignUpScreen.class.getSimpleName();
    private UserViewModel viewModel;
    private GoogleLoginHelper googleLoginHelper;
    private FacebookLoginHelper facebookLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RemoteRepositoryDataStore remoteRepositoryDataStore = new RemoteRepositoryDataStore();
        RegisterUseCase registerUseCase = new RegisterUseCase(remoteRepositoryDataStore);
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(registerUseCase);
        viewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        RegisterUserLifecycleObserver registerUserLifecycleObserver = new RegisterUserLifecycleObserver(viewModel);
        getLifecycle().addObserver(registerUserLifecycleObserver);

        googleLoginHelper = new GoogleLoginHelper(this);
        facebookLoginHelper = new FacebookLoginHelper(this, facebookCredentials -> {
            if (facebookCredentials != null)
                registerWithFacebook(facebookCredentials);
        });

        viewModel.getUser().observe(this, registerUserResponse -> {
            if (registerUserResponse.getEventType() == RegisterResponseEventType.SUCCESS) {
                Toast.makeText(SignUpScreen.this, "User registered: "
                        + registerUserResponse.getEventType() + " : "
                        + registerUserResponse.getUser().getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpScreen.this, "User registered: "
                        + registerUserResponse.getEventType() + " : "
                        + registerUserResponse.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_facebook)
    public void getFacebookCredentials() {
        facebookLoginHelper.getFacebookCredentials();
    }

    @OnClick(R.id.btn_google)
    public void getGoogleCredentials() {
        googleLoginHelper.getGoogleCredentials();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginHelper.onLoginResult(requestCode, resultCode, data);

        GoogleSignInAccount googleSignInAccount = googleLoginHelper.onLoginResult(requestCode, resultCode, data);
        if (googleSignInAccount != null) {
            registerWithGoogle(googleSignInAccount);
        }
    }

    @OnClick(R.id.btn_register)
    public void registerWithEmailAndPassword() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        User user = new User();
        user.setEmail("gelaneeem123@gmail.com");
        user.setPassword("password");
        registerUserRequest.setAccountType(AccountType.PASSWORD);
        registerUserRequest.setUser(user);
        viewModel.registerUser(registerUserRequest);
    }

    private void registerWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setAccountType(AccountType.GOOGLE);
        registerUserRequest.setAuthCredential(credential);
        viewModel.registerUser(registerUserRequest);
    }

    private void registerWithFacebook(String facebookCredentials) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(facebookCredentials);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setAccountType(AccountType.FACEBOOK);
        registerUserRequest.setAuthCredential(authCredential);
        viewModel.registerUser(registerUserRequest);
    }
}
