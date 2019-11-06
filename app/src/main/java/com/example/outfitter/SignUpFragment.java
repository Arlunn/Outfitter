package com.example.outfitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        Activity activity = getActivity();

        if (activity != null) {

            mUsernameEditText = v.findViewById(R.id.usernameField);
            mPasswordEditText = v.findViewById(R.id.passwordField);
            mConfirmPasswordEditText = v.findViewById(R.id.confirmPasswordField);

            Button cancelButton = v.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(this);
            Button createAccountButton = v.findViewById(R.id.createAccountButton);
            createAccountButton.setOnClickListener(this);

            //int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        }




        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentActivity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.cancelButton:
                    if (activity != null) {
                        activity.getSupportFragmentManager().popBackStack();
                    }
                    break;
                case R.id.createAccountButton:
                    createAccount();
                    break;
            }
        }
    }

    private void createAccount() {
        FragmentActivity activity = getActivity();
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        if (activity != null) {
            if (!username.isEmpty() && !password.isEmpty()) {
                AccountSingleton instance = AccountSingleton.get(activity.getApplicationContext());
                Account account = new Account(username, password);
                List<Account> accounts = instance.get(activity.getApplicationContext()).getAccounts();
                boolean foundAccount = false;
                for (Account acc : accounts) {
                    if (acc.getUsername().equals(username)) {
                        foundAccount = true;
                        break;
                    }
                }
                if (!foundAccount) {
                    instance.addAccount(account);
                    Toast.makeText(activity.getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    if (activity != null) {
                        activity.getSupportFragmentManager().popBackStack();
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();;
                }
            } else if (username.isEmpty()) {
                Toast.makeText(activity.getApplicationContext(), "Choose a username", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(activity.getApplicationContext(), "Choose a password", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(activity.getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
