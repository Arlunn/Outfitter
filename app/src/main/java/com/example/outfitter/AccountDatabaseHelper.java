package com.example.outfitter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OutfitterLogin.db";
    private static final int DATABASE_VERSION = 1;

    // Class name for logging.
    private final String TAG = getClass().getSimpleName();

    AccountDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + AccountSchema.AccountsTable.DATABASENAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccountSchema.AccountsTable.Columns.USERNAME + " TEXT, " +
                AccountSchema.AccountsTable.Columns.PASSWORD + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AccountSchema.AccountsTable.DATABASENAME);
        onCreate(sqLiteDatabase);
    }
}
