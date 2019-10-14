package com.example.outfitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private AccountSingleton mAccountSingleton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null) {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.fragment_signup_land, container, false);
            } else {
                v = inflater.inflate(R.layout.fragment_login, container, false);
            }
        }
        else {
            v = inflater.inflate(R.layout.fragment_login, container, false);
        }

        mUsernameEditText = v.findViewById(R.id.usernameField);
        mPasswordEditText = v.findViewById(R.id.passwordField);

        Button cancelButton = v.findViewById(R.id.cancelButton);
        if (cancelButton != null) {
            cancelButton.setOnClickListener(this);
        }
        Button createAccountButton = v.findViewById(R.id.createAccountButton);
        if (createAccountButton != null) {
            createAccountButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.cancelButton:
                    activity.finish();
                    break;
                case R.id.createAccountButton:
                    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    FragmentManager fm = getFragmentManager();
                    Fragment fragment = new LoginFragment();
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                        if (fm != null) {
                            fm.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack("account_fragment")
                                    .commit();
                        }
                    } else {
                        if (fm != null) {
                            fm.beginTransaction()
                                    .add(R.id.fragment_container, fragment)
                                    .addToBackStack("account_fragment")
                                    .commit();
                        }
                    }
                    break;
            }
        }
    }
}
