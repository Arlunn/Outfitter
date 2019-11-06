package com.example.outfitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.reflect.Method;


public class LoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "outfitter.USERNAME";

    //to turn off logging turn verbose false
    private static final boolean VERBOSE = true;
    private static final String TAG = "LoginActivity";

    private FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountSingleton.get(getApplicationContext());
        PostSingleton.get(getApplicationContext());
        setContentView(R.layout.activity_login);
        fm = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadFragment(new LoginFragment());

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
