package com.example.kasparsfisers.loginapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class Display extends AppCompatActivity {
        TextView greetings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        greetings = (TextView)findViewById(R.id.intro);
        SharedPreferences preferences = getSharedPreferences(getString(R.string.preffs), MODE_PRIVATE);
        String display = preferences.getString(getString(R.string.displayInfo), "");
        greetings.setText(display);


    }
}
