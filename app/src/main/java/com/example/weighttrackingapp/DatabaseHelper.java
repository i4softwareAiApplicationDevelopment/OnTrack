package com.example.weighttrackingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constant names
    public static final String DATABASE_NAME = "weighttracker.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_USER = "USERS";
    public static final String ID = "_ID";
    public static final String USER_NAME = "userName";
    public static final String USER_PASSWORD = "password";
    public static final String DATABASE_TABLE_WEIGHTS = "WEIGHTS";
    public static final String ACCOUNT_ID = "accountID";
    public static final String USER_WEIGHT = "weight";
    public static String DATE = "date";


    // Query for creating User and Weights table
    private static final String CREATE_DB_USERS = "CREATE TABLE " + DATABASE_TABLE_USER + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT NOT NULL, " + USER_PASSWORD + " );";

    private static final String CREATE_DB_WEIGHTS = "CREATE TABLE " + DATABASE_TABLE_WEIGHTS + " ( " +
            ACCOUNT_ID + " INTEGER, " + USER_WEIGHT + " INTEGER NOT NULL, " + DATE + " );";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables on initiation
        db.execSQL(CREATE_DB_USERS);
        db.execSQL(CREATE_DB_WEIGHTS);
    }

    @Override
    // If change, update table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS weights");
    }

}
