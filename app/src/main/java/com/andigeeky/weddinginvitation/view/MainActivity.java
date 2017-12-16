package com.andigeeky.weddinginvitation.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.data.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.domain.RegisterUseCase;
import com.andigeeky.weddinginvitation.domain.service.RegisterUserRequest;
import com.andigeeky.weddinginvitation.model.AccountType;
import com.andigeeky.weddinginvitation.model.User;
import com.andigeeky.weddinginvitation.presentation.UserViewModel;
import com.andigeeky.weddinginvitation.presentation.UserViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RemoteRepositoryDataStore remoteRepositoryDataStore = new RemoteRepositoryDataStore();
        RegisterUseCase registerUseCase = new RegisterUseCase(remoteRepositoryDataStore);
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(registerUseCase);
        UserViewModel viewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);


        findViewById(R.id.btn_register).setOnClickListener(view -> {
            registerWithEmailAndPassword(viewModel);
        });
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
}
