package com.example.outfitter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ChooseOutfitActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "outfitter.USERNAME";

    //to turn off logging turn verbose false
    private static final boolean VERBOSE = true;
    private static final String TAG = "LoginActivity";

    private FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountSingleton.get(getApplicationContext());
        setContentView(R.layout.activity_choose_outfit);
        fm = getSupportFragmentManager();

        loadFragment(new OutfitsChooseFragment());

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
