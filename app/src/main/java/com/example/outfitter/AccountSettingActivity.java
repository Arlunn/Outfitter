package com.example.outfitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener, FragmentChangeInterface{
    private String username;

    private final static String USERNAME_PREFERENCE = "name";

    private TextView mUsernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_accountsettings);

        username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(USERNAME_PREFERENCE, "username");
        mUsernameTextView = (TextView) findViewById(R.id.text_profile);

        Button mDeleteAccount = (Button) this.findViewById(R.id.deleteAccountButton);
        mDeleteAccount.setOnClickListener(this);
        Button mUpdtePassword = (Button) this.findViewById(R.id.updatePasswordButton);
        mUpdtePassword.setOnClickListener(this);
        Button mSignOut = (Button) this.findViewById(R.id.signOutButton);
        mSignOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteAccountButton:
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.remove(USERNAME_PREFERENCE);
                editor.commit();

                AccountSingleton.get(this).deleteUser(username);

                // Bring up login screen again
                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                break;
            case R.id.signOutButton:
                settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
                editor = settings.edit();
                editor.remove(USERNAME_PREFERENCE);
                editor.commit();

                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                break;
        }
    }

    @Override
    public boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, LoginFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return false;
    }
}
