package com.example.kasparsfisers.loginapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import com.example.kasparsfisers.loginapp.LocationContract.LocationEntry;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText logUser, logPass;
    Button login, register, location;
    ImageView imgLoading;


    FragmentManager fm = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logUser = (EditText) findViewById(R.id.loginUser);
        logPass = (EditText) findViewById(R.id.loginPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnLoginReg);
        location = (Button) findViewById(R.id.btnLoc);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        location.setOnClickListener(this);
        imgLoading = (ImageView) findViewById(R.id.imageRotate);

        if(LocationService.isInstanceCreated()){
            location.setText(R.string.stop);
        } else {
            location.setText(R.string.start);
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            imgLoading.startAnimation(animation);

            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
            logUser.setVisibility(View.GONE);
            logPass.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {

                    String user = logUser.getText().toString();
                    String pass = logPass.getText().toString();

                    SharedPreferences preferences = getSharedPreferences(getString(R.string.preffs), MODE_PRIVATE);
                    String userDetails = preferences.getString(user + pass + getString(R.string.userInfo), "");
                    if (userDetails.equals("")) {
                        Toast.makeText(MainActivity.this, R.string.denied, Toast.LENGTH_SHORT).show();
                        login.setVisibility(View.VISIBLE);
                        register.setVisibility(View.VISIBLE);
                        logUser.setVisibility(View.VISIBLE);
                        logPass.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                    } else {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(getString(R.string.displayInfo), userDetails);
                        editor.apply();
                        Intent toLogin = new Intent(MainActivity.this, Display.class);
                        startActivity(toLogin);
                        login.setVisibility(View.VISIBLE);
                        register.setVisibility(View.VISIBLE);
                        logUser.setVisibility(View.VISIBLE);
                        logPass.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                    }

                }
            }, 3000);

        }

        if (v.getId() == R.id.btnLoginReg) {
            AlertDFragment alertdFragment = new AlertDFragment();
            // Show Alert DialogFragment
            alertdFragment.show(fm, "");


        }

        if (v.getId() == R.id.btnLoc) {

            if(!LocationService.isInstanceCreated()){

                startService(new Intent(getBaseContext(), LocationService.class));
                location.setText(R.string.stop);

            } else {
                stopService(new Intent(getBaseContext(), LocationService.class));
                location.setText(R.string.start);
            }

        }
    }



}
