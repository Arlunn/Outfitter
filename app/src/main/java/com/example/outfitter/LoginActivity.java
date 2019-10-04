package com.example.outfitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    public final static String EXTRA_MESSAGE = "outfitter.USERNAME";

    //to turn off logging turn verbose false
    private static final boolean VERBOSE = true;
    private static final String TAG = "LoginActivity";


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

    @Override
    public void onResume() {
        super.onResume();
        if (VERBOSE) Log.v(TAG, "+ ON RESUME +");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (VERBOSE) Log.v(TAG, "- ON PAUSE -");
    }
}
