package com.andigeeky.weddinginvitation.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.common.FacebookLoginHelper;
import com.andigeeky.weddinginvitation.common.GoogleLoginHelper;
import com.andigeeky.weddinginvitation.common.utility.RegisterRequestMapper;
import com.andigeeky.weddinginvitation.common.utility.ValidationUtils;
import com.andigeeky.weddinginvitation.databinding.ActivitySignUpBinding;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.presentation.SignViewModel;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SignUpScreen extends BaseActivity {
    @Inject
    GoogleLoginHelper googleLoginHelper;
    @Inject
    FacebookLoginHelper facebookLoginHelper;
    @Inject
    SignViewModel viewModel;

    private ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        activitySignUpBinding.setHandlers(new Handler());

        facebookLoginHelper.registerHandler(facebookAuthHandler);

        viewModel.getUser().observe(this, this::handleResult);
    }

    private void handleResult(Resource<AuthResult> result) {
        switch (result.status) {
            case LOADING:
                startLoading();
                break;
            case SUCCESS:
                stopLoading();
                Toast.makeText(SignUpScreen.this, "User registered: SUCCESS   -------"
                        + (result.data != null ? result.data.getUser().getEmail() : null), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UploadActivity.class));
                break;
            case ERROR:
                stopLoading();
                Toast.makeText(SignUpScreen.this, "User registered: FAILED  -------"
                        + result.message, Toast.LENGTH_SHORT).show();
                break;
        }
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

    public void registerWithEmailAndPassword(Credentials credentials) {
        viewModel.registerUser(RegisterRequestMapper.registerWithEmailAndPassword(credentials));
    }

    private void registerWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        viewModel.registerUser(RegisterRequestMapper.registerWithGoogle(credential));
    }

    private void registerWithFacebook(String facebookCredentials) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(facebookCredentials);
        viewModel.registerUser(RegisterRequestMapper.registerWithFacebook(authCredential));
    }

    private boolean validateCredentials(Credentials credentials) {
        clearErrors();
        if (!ValidationUtils.isValidEmail(credentials.getEmail())) {
            activitySignUpBinding.edtEmail.requestFocus();
            activitySignUpBinding.edtEmail.setErrorEnabled(true);
            activitySignUpBinding.edtEmail.setError(getString(R.string.err_email));
            return false;
        }

        if (!ValidationUtils.isPasswordValid(credentials.getPassword())) {
            activitySignUpBinding.editPwd.requestFocus();
            activitySignUpBinding.editPwd.setErrorEnabled(true);
            activitySignUpBinding.editPwd.setError(getString(R.string.err_password));
            return false;
        }

        if (!ValidationUtils.isPasswordValid(credentials.getCPassword())) {
            activitySignUpBinding.edtConfirmPwd.requestFocus();
            activitySignUpBinding.edtConfirmPwd.setErrorEnabled(true);
            activitySignUpBinding.edtConfirmPwd.setError(getString(R.string.err_password));
            return false;
        }

        if (!ValidationUtils.isPasswordMatch(credentials.getPassword(), credentials.getCPassword())) {
            activitySignUpBinding.edtConfirmPwd.requestFocus();
            activitySignUpBinding.edtConfirmPwd.setErrorEnabled(true);
            activitySignUpBinding.edtConfirmPwd.setError(getString(R.string.err_password_match));
            return false;
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void clearErrors() {
        activitySignUpBinding.edtEmail.setErrorEnabled(false);
        activitySignUpBinding.edtEmail.setError(null);
        activitySignUpBinding.editPwd.setErrorEnabled(false);
        activitySignUpBinding.editPwd.setError(null);
        activitySignUpBinding.edtConfirmPwd.setErrorEnabled(false);
        activitySignUpBinding.edtConfirmPwd.setError(null);
    }

    private FacebookLoginHelper.FacebookAuthHandler facebookAuthHandler = new FacebookLoginHelper.FacebookAuthHandler() {
        @Override
        public void onComplete(String facebookCredentials) {
            registerWithFacebook(facebookCredentials);
        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public class Handler {
        public void onClickGoogle(View view) {
            googleLoginHelper.getGoogleCredentials();
        }

        public void onClickFacebook(View view) {
            facebookLoginHelper.getFacebookCredentials();
        }

        public void onClickEmail(View view) {
            Credentials credentials = new Credentials();
            if (activitySignUpBinding.edtEmail.getEditText() != null) {
                credentials.setEmail(activitySignUpBinding.edtEmail.getEditText().getText().toString());
            }

            EditText edtConfirmPwd = activitySignUpBinding.edtConfirmPwd.getEditText();
            EditText edtPwd = activitySignUpBinding.editPwd.getEditText();
            if (edtPwd != null && edtConfirmPwd != null) {
                credentials.setPassword(edtPwd.getText().toString());
                credentials.setCPassword(edtConfirmPwd.getText().toString());
            }

            if (validateCredentials(credentials)) {
                registerWithEmailAndPassword(credentials);
            }
        }
    }
}
