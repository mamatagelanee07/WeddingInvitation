package com.andigeeky.weddinginvitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andigeeky.weddinginvitation.auth.AuthHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText) findViewById(R.id.edt_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.edt_confirm_pwd)).getText().toString();
                AuthHelper authHelper = new AuthHelper(MainActivity.this);
                authHelper.register(email, password);
            }
        });

    }
}
