package com.example.outfitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    public final static String EXTRA_MESSAGE = "outfitter.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(this, ProfileActivity.class);
                EditText username = (EditText) findViewById(R.id.usernameField);
                String message = username.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                */
            }


        });
    }
}
