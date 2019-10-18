package com.example.outfitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccountSingleton {
    private static final String DATABASE_NAME = "Outfitter.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INSERT_COMMAND = "INSERT INTO " + AccountSchema.AccountsTable.DATABASENAME + " (username, password) VALUES (?, ?)" ;
    private static final String UPDATE_COMMAND = "UPDATE " + AccountSchema.AccountsTable.DATABASENAME + " SET password = ? "
            + "WHERE username = ?";


    private static AccountSingleton sInstance;

    private Account currentUser;

    private SQLiteDatabase mDatabase;

    private DatabaseReference mDatabaseF;


    private AccountSingleton(Context context) {
        AccountDatabaseHelper helper = new AccountDatabaseHelper(context.getApplicationContext());
        mDatabase = helper.getWritableDatabase();

        mDatabaseF = FirebaseDatabase.getInstance().getReference();
    }

    public static AccountSingleton get(Context context) {
        if (sInstance == null) {
            sInstance = new AccountSingleton(context);
        }
        return sInstance;
    }

    public void deleteUser(String username) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(AccountSchema.AccountsTable.DATABASENAME, AccountSchema.AccountsTable.Columns.USERNAME + "=?" , new String[]{username});
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    public void updatePassword(Account account) {
        ContentValues contentValues = getContentValues(account);

        mDatabase.beginTransaction();
        try {
            SQLiteStatement stmt = mDatabase.compileStatement(INSERT_COMMAND);
            stmt.bindString(1, contentValues.getAsString(AccountSchema.AccountsTable.Columns.USERNAME));
            stmt.bindString(2, contentValues.getAsString(AccountSchema.AccountsTable.Columns.PASSWORD));
            stmt.executeUpdateDelete();
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    private static ContentValues getContentValues(Account account) {
        ContentValues values = new ContentValues();
        values.put(AccountSchema.AccountsTable.Columns.USERNAME, account.getUsername());
        values.put(AccountSchema.AccountsTable.Columns.PASSWORD, account.getPassword());

        return values;
    }

    void addAccount(Account account) {
        ContentValues contentValues = getContentValues(account);

        mDatabase.beginTransaction();
        try {
            SQLiteStatement stmt = mDatabase.compileStatement(INSERT_COMMAND);
            stmt.bindString(1, contentValues.getAsString(AccountSchema.AccountsTable.Columns.USERNAME));
            stmt.bindString(2, contentValues.getAsString(AccountSchema.AccountsTable.Columns.PASSWORD));
            stmt.executeInsert();
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }


    }

    List<Account> getAccounts() {
        Cursor cursor = mDatabase.query(
                AccountSchema.AccountsTable.DATABASENAME,
                null, // columns; null selects all columns
                null,
                null,
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );


        CursorWrapper wrapper = new CursorWrapper(cursor);

        List<Account> accountList = new ArrayList<>();


        wrapper.moveToFirst();
        while (!wrapper.isAfterLast()) {
            String name = wrapper.getString(wrapper.getColumnIndex(AccountSchema.AccountsTable.Columns.USERNAME));
            String password = wrapper.getString(wrapper.getColumnIndex(AccountSchema.AccountsTable.Columns.PASSWORD));

            accountList.add(new Account(name, password));
            wrapper.moveToNext();
        }

        return accountList;
    }


    String getUser() {
        return currentUser.getUsername();
    }
}
