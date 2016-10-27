package com.example.kasparsfisers.loginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kasparsfisers.loginapp.LocationContract.LocationEntry;

public class LocationDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;



    public LocationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOCATION_TABLE ="CREATE TABLE " + LocationEntry.TABLE_NAME + " ("
                + LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, "
                + LocationEntry.COLUMN_LONGITUDE + " REAL NOT NULL, "
                + LocationEntry.COLUMN_ACCURACY + " REAL NOT NULL, "
                + LocationEntry.COLUMN_LOCNAME + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static void insertCoordinates(Context context, double lat, double lon, double acc, String locName) {

        LocationDbHelper mDbHelper = new LocationDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LocationContract.LocationEntry.COLUMN_LATITUDE, lat);
        values.put(LocationContract.LocationEntry.COLUMN_LONGITUDE,lon);
        values.put(LocationContract.LocationEntry.COLUMN_ACCURACY, acc);
        values.put(LocationContract.LocationEntry.COLUMN_LOCNAME, locName);
        db.insert(LocationContract.LocationEntry.TABLE_NAME, null, values);

    }


}
