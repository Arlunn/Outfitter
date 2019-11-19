package com.example.outfitter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeInterface {

    private final static String USERNAME_PREFERENCE = "name";

    public static final String PREVIOUS_FRAGMENT = "previousFragment";
    public int prevFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = settings.getString(USERNAME_PREFERENCE, "username");
        AccountSingleton.get(this).updateClothesUris(username);
        OutfitSingleton.get(this).updateOutfitsUris(username);

        if (savedInstanceState != null) {
            prevFragment = savedInstanceState.getInt(PREVIOUS_FRAGMENT, 0);
        }

        if(prevFragment == 0 || prevFragment == 2) {
            loadFragment(new PostFragment());
            prevFragment = 2;
        }else if(prevFragment ==1){
            loadFragment(new FeedFragment());
        }else if(prevFragment ==3){
            loadFragment(new ProfileFragment());
        }
        /**Passing each menu ID as a set of Ids because each
        menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);**/
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(PREVIOUS_FRAGMENT, prevFragment);
    }

    @Override
    public boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(fragment.toString()).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_feed:
                fragment = new FeedFragment();
                prevFragment = 1;
                break;
            case R.id.navigation_post:
                fragment = new PostFragment();
                prevFragment = 2;
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                prevFragment = 3;
                break;
        }

        return loadFragment(fragment);
    }
}
