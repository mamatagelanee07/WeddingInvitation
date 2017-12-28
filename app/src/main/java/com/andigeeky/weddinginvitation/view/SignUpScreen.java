package com.andigeeky.weddinginvitation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
import com.andigeeky.weddinginvitation.common.utility.RegisterRequestMapper;
import com.andigeeky.weddinginvitation.domain.service.RegisterResponseEventType;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserResponse;
import com.andigeeky.weddinginvitation.presentation.UserViewModel;
import com.andigeeky.weddinginvitation.presentation.UserViewModelFactory;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class SignUpScreen extends AppCompatActivity {
    private static final String TAG = SignUpScreen.class.getSimpleName();

    @BindView(R.id.btn_register)
    public Button btnEmailSignUp;

    @Inject
    UserViewModelFactory userViewModelFactory;
    @Inject
    GoogleLoginHelper googleLoginHelper;
    @Inject
    FacebookLoginHelper facebookLoginHelper;
    @Inject
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        facebookLoginHelper.registerHandler(new FacebookLoginHelper.FacebookAuthHandler() {
            @Override
            public void onComplete(String facebookCredentials) {
                SignUpScreen.this.registerWithFacebook(facebookCredentials);
            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        RegisterUserResponse response = viewModel.getUser().getValue();
        if (response != null && response.getUser() != null) {
            btnEmailSignUp.setEnabled(false);
            Toast.makeText(this, "User is already logged in!!", Toast.LENGTH_SHORT).show();
        }

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
        viewModel.registerUser(RegisterRequestMapper.registerWithEmailAndPassword("gelaneem@gmail.com", "password"));
    }

    private void registerWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        viewModel.registerUser(RegisterRequestMapper.registerWithGoogle(credential));
    }

    private void registerWithFacebook(String facebookCredentials) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(facebookCredentials);
        viewModel.registerUser(RegisterRequestMapper.registerWithFacebook(authCredential));
    }
}
