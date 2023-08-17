package com.example.weighttrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;

public class  DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx) {
        context = ctx;
    }

    // Start database manager
    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    // Closing manager
    public void close() {
        dbHelper.close();
    }

    // Insert new users into database
    public void insert(String username, String password) {
        ContentValues contentValues = new ContentValues();
        // Get username and passwords
        contentValues.put(DatabaseHelper.USER_NAME, username);
        contentValues.put(DatabaseHelper.USER_PASSWORD, password);
        // Insert into database
        database.insert(DatabaseHelper.DATABASE_TABLE_USER, null, contentValues);
    }
    // Grab all data in weights table
    public Cursor fetch() {
        String [] columns = new String[] {DatabaseHelper.ACCOUNT_ID, DatabaseHelper.USER_WEIGHT, DatabaseHelper.DATE};
        // Return cursor of weights table
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE_WEIGHTS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    // Update table info
    public int update(long _id, String weight, String date) {
        ContentValues contentValues = new ContentValues();
        // Parse user weights and date
        contentValues.put(DatabaseHelper.USER_WEIGHT, weight);
        contentValues.put(DatabaseHelper.DATE, date);
        // Update value with new value
        int ret = database.update(DatabaseHelper.DATABASE_TABLE_WEIGHTS, contentValues, DatabaseHelper.ID + "=" + _id, null);
        return ret;
    }
    // Deleting data in weights table
    public void delete(String weight) {
        database.delete(DatabaseHelper.DATABASE_TABLE_WEIGHTS, DatabaseHelper.USER_WEIGHT + "=" + weight, null);
    }

    // Check if username and password is in database
    public Boolean checkUserNamePassword(String username, String password) {
        // If match, logs user in
        Cursor cursor = database.rawQuery("SELECT * FROM users where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // Adding weight to database table
    public void addWeight(long userID, int weight, String date) {
        ContentValues contentValues = new ContentValues();
        // Collect userID, weight, and date
        contentValues.put(DatabaseHelper.ACCOUNT_ID, userID);
        contentValues.put(DatabaseHelper.USER_WEIGHT, weight);
        contentValues.put(DatabaseHelper.DATE, date);
        // Insert information into weights table
        database.insert(DatabaseHelper.DATABASE_TABLE_WEIGHTS, null, contentValues);
    }
}
