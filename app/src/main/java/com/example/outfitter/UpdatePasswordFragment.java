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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class UpdatePasswordFragment extends Fragment implements View.OnClickListener {
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mUsernameTextView;
    private final static String USERNAME_PREFERENCE = "name";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        Activity activity = getActivity();

        if (activity != null) {

            mPasswordEditText = v.findViewById(R.id.newPasswordField);
            mConfirmPasswordEditText = v.findViewById(R.id.confirmPasswordField);

            String username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
            mUsernameTextView = (TextView) v.findViewById(R.id.text_profile);
            mUsernameTextView.setText(username);


            Button cancelButton = v.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(this);
            Button createAccountButton = v.findViewById(R.id.updatePasswordButton);
            createAccountButton.setOnClickListener(this);

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
                case R.id.updatePasswordButton:
                    updatePassword();
                    break;
            }
        }
    }

    private void updatePassword() {
        FragmentActivity activity = getActivity();
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        if (activity != null) {
            if (password.equals(confirmPassword)) {
                AccountSingleton instance = AccountSingleton.get(activity.getApplicationContext());
                String username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
                Account account = new Account(username, password);
                instance.updatePassword(account);
                Toast.makeText(activity.getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                if (activity != null) {
                    activity.getSupportFragmentManager().popBackStack();
                }
            } else {
                Toast.makeText(activity.getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
