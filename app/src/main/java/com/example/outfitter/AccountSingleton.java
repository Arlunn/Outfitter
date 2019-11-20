package com.example.outfitter;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Debug;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;

public class AccountSingleton {

    private static AccountSingleton sInstance;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private final String TAG = "AccountSingleton";

    List<Account> list = new ArrayList<>();
    List<String> clothesUris = new ArrayList<>();


    private AccountSingleton(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    try {
                        String username = data.child("username").getValue().toString();
                        String password = data.child("password").getValue().toString();
                        String decoded = Encrypter.decrypt(password);
                        list.add(new Account(username, decoded));
                    } catch (Exception e) {
                        Log.d(TAG, "Failed to get account");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

        }
        });
        mStorage = FirebaseStorage.getInstance().getReference().child("images");

    }

    public static AccountSingleton get(Context context) {
        if (sInstance == null) {
            sInstance = new AccountSingleton(context);
        }
        return sInstance;
    }

    public void deleteUser(String username) {
        mDatabase.child(username).removeValue();
    }

    public void updatePassword(Account account) {
        mDatabase.child(account.getUsername()).child("password").setValue(account.getPassword());
    }

    public void addAccount(Account account) {
        String encodedPassword = null;
        try {
            encodedPassword = Encrypter.encrypt(account.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        account.setPassword(encodedPassword);
            mDatabase.child(account.getUsername()).setValue(account);

    }

    public void updateClothesUris(String username) {
        mDatabase.child(username).child("clothesList")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HashMap<String, String> uriMap = (HashMap<String, String>) snapshot.getValue();
                            clothesUris.add(uriMap.get("image_url"));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public List<String> getClothesUris() {
        return clothesUris;
    }

    public void addImage(byte[] data, String username) {
        StorageReference ref =  mStorage.child(String.valueOf(UUID.randomUUID()));
        UploadTask uploadTask = ref.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.d("Singleton", "success upload");
                    Uri downloadUri = task.getResult();
                    HashMap map = new HashMap();
                    map.put("image_url",downloadUri.toString());
                    mDatabase.child(username).child("clothesList").push().updateChildren(map);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    List<Account> getAccounts() {
        return list;
    }

}
