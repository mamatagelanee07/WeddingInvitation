package com.andigeeky.weddinginvitation.view.fragments;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.binding.FragmentDataBindingComponent;
import com.andigeeky.weddinginvitation.common.utility.ValidationUtils;
import com.andigeeky.weddinginvitation.databinding.FragmentResetPasswordBinding;
import com.andigeeky.weddinginvitation.di.Injectable;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;
import com.andigeeky.weddinginvitation.presentation.RegisterUserViewModel;
import com.andigeeky.weddinginvitation.view.BaseActivity;
import com.andigeeky.weddinginvitation.view.LoginActivity;
import com.andigeeky.weddinginvitation.view.vo.Credentials;

import javax.inject.Inject;

public class ResetPasswordFragment extends Fragment implements Injectable {

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    @Inject
    RegisterUserViewModel viewModel;
    private FragmentResetPasswordBinding dataBinding;

    public static ResetPasswordFragment create() {
        return new ResetPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password,
                container, false, dataBindingComponent);
        dataBinding.setHandlers(new Handler());
        return dataBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getResetPassword().observe(this, this::handleResetPassword);
    }

    private void handleResetPassword(Resource<Void> result) {
        switch (result.status) {
            case LOADING:
                ((BaseActivity) getActivity()).startLoading();
                break;
            case SUCCESS:
                ((BaseActivity) getActivity()).stopLoading();
                ((LoginActivity) getActivity()).navigationController.navigateToLogin();
                break;
            case ERROR:
                ((BaseActivity) getActivity()).stopLoading();
                Toast.makeText(getActivity(), "User registered: FAILED  -------"
                        + result.message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void resetPassword(Credentials credentials) {
        viewModel.resetPassword(credentials);
    }

    private boolean validateCredentials(Credentials credentials) {
        clearErrors();
        if (!ValidationUtils.isValidEmail(credentials.getEmail())) {
            dataBinding.edtEmail.requestFocus();
            dataBinding.edtEmail.setErrorEnabled(true);
            dataBinding.edtEmail.setError(getString(R.string.err_email));
            return false;
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void clearErrors() {
        dataBinding.edtEmail.setErrorEnabled(false);
        dataBinding.edtEmail.setError(null);
    }

    public class Handler {

        public void onClickForgotPassword(View view) {
            Credentials credentials = new Credentials();
            if (dataBinding.edtEmail.getEditText() != null) {
                credentials.setEmail(dataBinding.edtEmail.getEditText().getText().toString());
            }
            if (validateCredentials(credentials)) {
                resetPassword(credentials);
            }
        }
    }
}
