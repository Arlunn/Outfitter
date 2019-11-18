package com.example.outfitter;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class UpdatePasswordFragment extends AppCompatActivity implements View.OnClickListener {
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mUsernameTextView;
    private final static String USERNAME_PREFERENCE = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_change_password);

            mPasswordEditText = findViewById(R.id.newPasswordField);
            mConfirmPasswordEditText = findViewById(R.id.confirmPasswordField);

            String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(USERNAME_PREFERENCE, "username");
            mUsernameTextView = (TextView) findViewById(R.id.text_profile);
            mUsernameTextView.setText(username);


            Button cancelButton = findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(this);
            Button createAccountButton = findViewById(R.id.updatePasswordButton);
            createAccountButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

            switch (view.getId()) {
                case R.id.cancelButton:
                    getSupportFragmentManager().popBackStack();

                    break;
                case R.id.updatePasswordButton:
                    this.updatePassword();
                    break;
            }

    }

    public void updatePassword() {
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

            if (password.equals(confirmPassword)) {
                AccountSingleton instance = AccountSingleton.get(getApplicationContext());
                String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(USERNAME_PREFERENCE, "username");
                Account account = new Account(username, password);
                instance.updatePassword(account);
                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();

                    getSupportFragmentManager().popBackStack();

            } else {
                Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        }
    }



