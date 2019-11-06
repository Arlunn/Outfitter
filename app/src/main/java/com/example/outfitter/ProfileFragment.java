package com.example.outfitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static com.example.outfitter.LoginFragment.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private String username;

    private final static String USERNAME_PREFERENCE = "name";

    private TextView mUsernameTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
        mUsernameTextView = (TextView) v.findViewById(R.id.text_profile);
        //mUsernameTextView.setText(username);
        /**
         Button mDeleteAccount = (Button) v.findViewById(R.id.deleteAccountButton);
         mDeleteAccount.setOnClickListener(this);
         Button mUpdtePassword = (Button) v.findViewById(R.id.updatePasswordButton);
         mUpdtePassword.setOnClickListener(this);
         Button mSignOut = (Button) v.findViewById(R.id.signOutButton);
         mSignOut.setOnClickListener(this);
         **/

        Button mSetting = (Button) v.findViewById(R.id.settings);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccountSettingActivity.class));
            }
        });
        Button mCloset = (Button) v.findViewById(R.id.closet);
        mCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ClosetFragmentPager.class));
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        /**
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
         case R.id.updatePasswordButton:
         ((FragmentChangeInterface) getActivity()).loadFragment(new UpdatePasswordFragment());
         break;
         case R.id.signOutButton:
         settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
         editor = settings.edit();
         editor.remove(USERNAME_PREFERENCE);
         editor.commit();

         startActivity(new Intent(getActivity(), LoginActivity.class));
         getActivity().finish();
         break;
         }
         **/
    }
    /**
     private void setupToolbar(){
     Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
     setSupportActionBar(toolbar);

     ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
     profileMenu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    Log.d(TAG, "onClick: account setting");
    Intent intent = new Intent(mContext, AccountSettingActivity.class);
    startActivity(intent);
    }
    });
     }

     private void setSupportActionBar(Toolbar toolbar) {
     }
     **/

}
