package com.andigeeky.weddinginvitation.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
import com.andigeeky.weddinginvitation.common.utility.RegisterRequestMapper;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Status;
import com.andigeeky.weddinginvitation.presentation.SignViewModel;
import com.andigeeky.weddinginvitation.presentation.UserViewModelFactory;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class SignUpScreen extends BaseActivity {
    private static final String TAG = SignUpScreen.class.getSimpleName();
    ProgressDialog progress;
    @BindView(R.id.btn_register)
    public Button btnEmailSignUp;

    @Inject
    UserViewModelFactory userViewModelFactory;
    @Inject
    GoogleLoginHelper googleLoginHelper;
    @Inject
    FacebookLoginHelper facebookLoginHelper;
    @Inject
    SignViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        Resource<AuthResult> response = viewModel.getUser().getValue();

        if (response != null) {
            if (response.status == Status.SUCCESS && response.data.getUser() != null) {
                btnEmailSignUp.setEnabled(false);
                Toast.makeText(this, "User is already logged in!!", Toast.LENGTH_SHORT).show();
            }
        }
        viewModel.getUser().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    startLoading();
                    break;
                case SUCCESS:
                    stopLoading();
                    Toast.makeText(SignUpScreen.this, "User registered: SUCCESS   -------"
                            + result.data.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, UploadActivity.class));
                    break;
                case ERROR:
                    stopLoading();
                    Toast.makeText(SignUpScreen.this, "User registered: FAILED  -------"
                            + result.message, Toast.LENGTH_SHORT).show();
                    break;
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
        String email = ((TextInputLayout) findViewById(R.id.edt_email)).getEditText().getText() == null ? null : ((TextInputLayout) findViewById(R.id.edt_email)).getEditText().getText().toString();
        String password = ((TextInputLayout) findViewById(R.id.edit_pwd)).getEditText().getText() == null ? null : ((TextInputLayout) findViewById(R.id.edit_pwd)).getEditText().getText().toString();
        viewModel.registerUser(RegisterRequestMapper.registerWithEmailAndPassword(email, password));
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
