package com.andigeeky.weddinginvitation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andigeeky.weddinginvitation.auth.AuthHelper;
import com.andigeeky.weddinginvitation.auth.vo.User;
import com.andigeeky.weddinginvitation.newarchitecture.JobManagerFactory;
import com.andigeeky.weddinginvitation.newarchitecture.RegisterUseCase;
import com.andigeeky.weddinginvitation.newarchitecture.RemoteRepositoryDataStore;
import com.andigeeky.weddinginvitation.newarchitecture.UserViewModel;
import com.andigeeky.weddinginvitation.newarchitecture.UserViewModelFactory;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RemoteRepositoryDataStore remoteRepositoryDataStore = new RemoteRepositoryDataStore();
        RegisterUseCase registerUseCase = new RegisterUseCase(remoteRepositoryDataStore);
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(registerUseCase);
        UserViewModel viewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);


        findViewById(R.id.btn_register).setOnClickListener(view -> {
//                registerWithCredentials();
//                signIn();


            registerWithNewArch(viewModel);
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void registerWithNewArch(UserViewModel viewModel) {
        User user = new User();
        user.setEmail("gelaneeem123@gmail.com");
        user.setPassword("password");
        viewModel.registerUser(user);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void registerWithCredentials() {
        String email = ((EditText) findViewById(R.id.edt_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.edt_confirm_pwd)).getText().toString();
        AuthHelper authHelper = new AuthHelper(MainActivity.this);
        authHelper.register(email, password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        AuthHelper authHelper = new AuthHelper(this);
        authHelper.registerWithGoogle(credential);
    }
}
