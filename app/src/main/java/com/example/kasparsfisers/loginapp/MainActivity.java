package com.example.kasparsfisers.loginapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText logUser, logPass;
    Button login, register, location;
    ImageView imgLoading;
    int m = 0;
    FragmentManager fm = getSupportFragmentManager();

    ObjectAnimator a = new ObjectAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logUser = (EditText) findViewById(R.id.loginUser);
        logPass = (EditText) findViewById(R.id.loginPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnLoginReg);
        location = (Button) findViewById(R.id.btnLocation);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        location.setOnClickListener(this);
        imgLoading = (ImageView) findViewById(R.id.imageRotate);

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

                    SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                    String userDetails = preferences.getString(user + pass + "info", "");
                    if (userDetails.equals("")) {
                        Toast.makeText(MainActivity.this, "Wrong data provided", Toast.LENGTH_SHORT).show();
                        login.setVisibility(View.VISIBLE);
                        register.setVisibility(View.VISIBLE);
                        logUser.setVisibility(View.VISIBLE);
                        logPass.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                    } else {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("display", userDetails);
                        editor.commit();
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
            alertdFragment.show(fm, "Alert Dialog Fragment");

        }

        if (v.getId() == R.id.btnLocation) {
            if (m == 0) {
                startService(new Intent(getBaseContext(), LocationService.class));
                m = 1;
            } else {
                stopService(new Intent(getBaseContext(), LocationService.class));
                m = 0;
            }


        }
    }
}
