package com.example.medical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "ClientManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_USER = "User";

    // Column Names
    private static final String COLUMN_USERID = "UserID";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_GENDER = "Gender";
    private static final String COLUMN_AGE = "Age";
    private static final String COLUMN_DESCRIPTION = "Description";

    // Create Table Query with corrected space
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_GENDER + " TEXT NOT NULL, "
            + COLUMN_AGE + " INTEGER NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT NOT NULL );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);  // Create the User table
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);  // Recreate table
    }

    // Insert a new User into the database
    public boolean insertUser(String username, String password, String email, String phoneNumber, String address, String gender, int age, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add key-value pairs
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PHONE_NUMBER, phoneNumber);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_AGE, age);
        contentValues.put(COLUMN_DESCRIPTION, description);

        // Insert row and return result
        long result = db.insert(TABLE_USER, null, contentValues);

        // Return true if inserted, false otherwise
        return result != -1;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER, null);
    }

    // Update a User's details
    public boolean updateUser(int userID, String username, String password, String email, String phoneNumber, String address, String gender, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add key-value pairs
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PHONE_NUMBER, phoneNumber);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_AGE, age);

        // Update the row and return result
        int result = db.update(TABLE_USER, contentValues, COLUMN_USERID + " = ?", new String[]{String.valueOf(userID)});
        return result > 0;
    }

    // Delete a user
    public Integer deleteUser(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_USERID + " = ?", new String[]{String.valueOf(userID)});
    }

    // Get a specific user by UserID
    public String getUserPhoneNumber(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{"phone_number"}, "id = ?",
                new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            String phoneNumber = cursor.getString(0);
            cursor.close();
            return phoneNumber;
        }

        return null;
    }
    public Cursor getUserById(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERID + " = ?", new String[]{String.valueOf(userID)});
    }
}
