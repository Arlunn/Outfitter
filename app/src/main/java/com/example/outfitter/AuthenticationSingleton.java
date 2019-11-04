package com.example.outfitter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AuthenticationSingleton {
    private static AuthenticationSingleton sInstance;

    private FirebaseAuth mAuth;
    private AuthenticationSingleton(Context context) {
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthenticationSingleton get(Context context) {
        if (sInstance == null) {
            sInstance = new AuthenticationSingleton(context);
        }
        return sInstance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }
}
