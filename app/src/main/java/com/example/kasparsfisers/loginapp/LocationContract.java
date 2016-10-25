package com.example.kasparsfisers.loginapp;

import android.provider.BaseColumns;

public final class LocationContract {

    private LocationContract(){}

    public static abstract class LocationEntry implements BaseColumns {

        public static final String TABLE_NAME = "coordinates";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ACCURACY = "accuracy";

    }
}
