package com.example.kasparsfisers.loginapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;


public class Display extends AppCompatActivity {

    private LocationDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        mDbHelper = new LocationDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor todoCursor = db.rawQuery("SELECT  * FROM coordinates", null);

        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.List);
// Setup cursor adapter using cursor from last step
        LocationAdapter todoAdapter = new LocationAdapter(this, todoCursor);
// Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);
    }
}
