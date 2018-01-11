package com.andigeeky.weddinginvitation.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.binding.FragmentDataBindingComponent;
import com.andigeeky.weddinginvitation.common.FacebookUtils;
import com.andigeeky.weddinginvitation.common.GoogleUtils;
import com.andigeeky.weddinginvitation.common.utility.RegisterRequestMapper;
import com.andigeeky.weddinginvitation.common.utility.ValidationUtils;
import com.andigeeky.weddinginvitation.databinding.FragmentLoginBinding;
import com.andigeeky.weddinginvitation.di.Injectable;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.presentation.RegisterUserViewModel;
import com.andigeeky.weddinginvitation.storage.upload.UploadActivity;
import com.andigeeky.weddinginvitation.view.BaseActivity;
import com.andigeeky.weddinginvitation.view.LoginActivity;
import com.andigeeky.weddinginvitation.view.vo.Action;
import com.andigeeky.weddinginvitation.view.vo.Credentials;
import com.andigeeky.weddinginvitation.view.vo.Login;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

public class LoginFragment extends Fragment implements Injectable {

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    GoogleUtils googleUtils;
    FacebookUtils facebookUtils;

    @Inject
    RegisterUserViewModel viewModel;

    FragmentLoginBinding dataBinding;

    public static LoginFragment create() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false, dataBindingComponent);
        dataBinding.setHandlers(new Handler());
        setUserAction(Action.ACTION_LOGIN);
        return dataBinding.getRoot();
    }

    private void setUserAction(@Action.IAction int action) {
        Login login = new Login();
        login.setAction(action);

        if (action == Action.ACTION_REGISTER) {
            login.setTextButton(getString(R.string.btn_signup));
            login.setTextToggle(getString(R.string.title_login));
        } else if (action == Action.ACTION_LOGIN) {
            login.setTextButton(getString(R.string.btn_login));
            login.setTextToggle(getString(R.string.title_register));
        }
        dataBinding.setLogin(login);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        googleUtils = new GoogleUtils(getActivity());
        facebookUtils = new FacebookUtils(getActivity());
        facebookUtils.registerHandler(facebookAuthHandler);
        viewModel.getRegisteredUser().observe(this, this::handleRegisterResult);
        viewModel.getLogin().observe(this, this::handleLogin);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookUtils.onLoginResult(requestCode, resultCode, data);

        GoogleSignInAccount googleSignInAccount = googleUtils.onLoginResult(requestCode, resultCode, data);
        if (googleSignInAccount != null) {
            registerWithGoogle(googleSignInAccount);
        }
    }

    private void handleRegisterResult(Resource<AuthResult> result) {
        switch (result.status) {
            case LOADING:
                ((BaseActivity) getActivity()).startLoading();
                break;
            case SUCCESS:
                ((BaseActivity) getActivity()).stopLoading();
                startActivity(new Intent(getActivity(), UploadActivity.class));
                break;
            case ERROR:
                ((BaseActivity) getActivity()).stopLoading();
                Toast.makeText(getActivity(), "User registered: FAILED  -------"
                        + result.message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void handleLogin(Resource<AuthResult> result) {
        switch (result.status) {
            case LOADING:
                ((BaseActivity) getActivity()).startLoading();
                break;
            case SUCCESS:
                ((BaseActivity) getActivity()).stopLoading();
                startActivity(new Intent(getActivity(), UploadActivity.class));
                break;
            case ERROR:
                ((BaseActivity) getActivity()).stopLoading();
                Toast.makeText(getActivity(), "User registered: FAILED  -------"
                        + result.message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void registerWithEmailAndPassword(Credentials credentials) {
        viewModel.registerUser(RegisterRequestMapper.registerWithEmailAndPassword(credentials));
    }

    private void login(Credentials credentials) {
        viewModel.login(credentials);
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
            dataBinding.edtEmail.requestFocus();
            dataBinding.edtEmail.setErrorEnabled(true);
            dataBinding.edtEmail.setError(getString(R.string.err_email));
            return false;
        }

        if (!ValidationUtils.isPasswordValid(credentials.getPassword())) {
            dataBinding.editPwd.requestFocus();
            dataBinding.editPwd.setErrorEnabled(true);
            dataBinding.editPwd.setError(getString(R.string.err_password));
            return false;
        }

        if (dataBinding.getLogin().getAction() == Action.ACTION_REGISTER) {
            if (!ValidationUtils.isPasswordValid(credentials.getCPassword())) {
                dataBinding.edtConfirmPwd.requestFocus();
                dataBinding.edtConfirmPwd.setErrorEnabled(true);
                dataBinding.edtConfirmPwd.setError(getString(R.string.err_password));
                return false;
            }

            if (!ValidationUtils.isPasswordMatch(credentials.getPassword(), credentials.getCPassword())) {
                dataBinding.edtConfirmPwd.requestFocus();
                dataBinding.edtConfirmPwd.setErrorEnabled(true);
                dataBinding.edtConfirmPwd.setError(getString(R.string.err_password_match));
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void clearErrors() {
        dataBinding.edtEmail.setErrorEnabled(false);
        dataBinding.edtEmail.setError(null);
        dataBinding.editPwd.setErrorEnabled(false);
        dataBinding.editPwd.setError(null);
        dataBinding.edtConfirmPwd.setErrorEnabled(false);
        dataBinding.edtConfirmPwd.setError(null);
    }

    private FacebookUtils.FacebookAuthHandler facebookAuthHandler = new FacebookUtils.FacebookAuthHandler() {
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
            googleUtils.getGoogleCredentials();
        }

        public void onClickFacebook(View view) {
            facebookUtils.getFacebookCredentials();
        }

        public void onClickEmail(View view) {
            Credentials credentials = new Credentials();
            if (dataBinding.edtEmail.getEditText() != null) {
                credentials.setEmail(dataBinding.edtEmail.getEditText().getText().toString());
            }

            EditText edtConfirmPwd = dataBinding.edtConfirmPwd.getEditText();
            EditText edtPwd = dataBinding.editPwd.getEditText();
            if (edtPwd != null && edtConfirmPwd != null) {
                credentials.setPassword(edtPwd.getText().toString());
                credentials.setCPassword(edtConfirmPwd.getText().toString());
            }

            if (validateCredentials(credentials)) {
                if (dataBinding.getLogin().getAction() == Action.ACTION_REGISTER) {
                    registerWithEmailAndPassword(credentials);
                } else if (dataBinding.getLogin().getAction() == Action.ACTION_LOGIN) {
                    login(credentials);
                }
            }
        }

        public void onClickForgotPassword(View view) {
            if ((getActivity()) != null) {
                ((LoginActivity) getActivity()).navigationController.navigateToForgotPassword();
            }
        }

        public void onClickLogin(View view) {
            if (dataBinding.getLogin().getAction() == Action.ACTION_LOGIN) {
                setUserAction(Action.ACTION_REGISTER);
            } else if (dataBinding.getLogin().getAction() == Action.ACTION_REGISTER) {
                setUserAction(Action.ACTION_LOGIN);
            }
        }
    }
}
