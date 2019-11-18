package com.example.outfitter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = "t";
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mSignUpButton;
    private FragmentManager fm;
    private AccountSingleton mDbInstance;
    private final static String USERNAME_PREFERENCE = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        fm = getFragmentManager();

        mLoginButton = (Button) v.findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(this);
        mSignUpButton = (Button) v.findViewById(R.id.signupButton);
        mSignUpButton.setOnClickListener(this);
        mUsernameEditText = (EditText) v.findViewById(R.id.usernameField);
        mPasswordEditText = (EditText) v.findViewById(R.id.passwordField);
        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onClick(View v) {
        FragmentActivity activity = getActivity();

        if (activity != null) {
            switch (v.getId()) {
                case R.id.loginButton:
                    checkLogin();
                    break;
                case R.id.signupButton:
                    loadFragment(new SignUpFragment());
                    break;
            }
        }
    }

    private void checkLogin() {
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        Activity activity = getActivity();

        if (mDbInstance == null) {
            if (activity != null) {
                mDbInstance = AccountSingleton.get(activity.getApplicationContext());
            }
        }
        if (activity != null) {
            List<Account> accountList = mDbInstance.getAccounts();
            boolean foundAccount = false;
            for (Account account : accountList) {
                if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                    foundAccount = true;
                    break;
                }
            }

            if (accountList.size() > 0 && foundAccount) {
                // Save username as the name of the player
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(USERNAME_PREFERENCE, username);
                editor.apply();

                // Bring up the GameOptions screen
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            } else {
                Toast.makeText(activity.getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment, LoginFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return false;
    }
}