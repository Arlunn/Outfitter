package com.example.outfitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AccountSingleton {

    private static AccountSingleton sInstance;

    private DatabaseReference mDatabase;

    List<Account> list = new ArrayList<>();


    private AccountSingleton(Context context) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String username= data.child("username").getValue().toString();
                    String password= data.child("password").getValue().toString();
                    list.add(new Account(username,password));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    void addAccount(Account account) {
        mDatabase.child(account.getUsername()).setValue(account);
    }

    List<Account> getAccounts() {
        return list;
    }

}
