package com.example.outfitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private String username;

    private final static String USERNAME_PREFERENCE = "name";

    private TextView mUsernameTextView;

    private Button mDeleteAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);
        username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
        mUsernameTextView = (TextView) v.findViewById(R.id.text_profile);
        mUsernameTextView.setText(username);

        mDeleteAccount = (Button) v.findViewById(R.id.deleteAccountButton);
        mDeleteAccount.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteAccountButton:
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.remove(USERNAME_PREFERENCE);
                editor.commit();

                AccountSingleton.get(getContext()).deleteUser(username);

                // Bring up login screen again
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
