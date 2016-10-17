package com.example.kasparsfisers.loginapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
EditText logUser, logPass;
    Button login,register;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logUser = (EditText) findViewById(R.id.loginUser);
        logPass = (EditText) findViewById(R.id.loginPass);
        login = (Button)findViewById(R.id.btnLogin);
        register =(Button)findViewById(R.id.btnLoginReg);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            String user = logUser.getText().toString();
            String pass = logPass.getText().toString();

            SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
            String userDetails = preferences.getString(user + pass + "info", "");
            if (userDetails.equals("")){
                Toast.makeText(this, "Wrong login data provided", Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("display", userDetails);
                editor.commit();
                Intent toLogin = new Intent(MainActivity.this,Display.class);
                startActivity(toLogin);
            }

        }

        if(v.getId() == R.id.btnLoginReg){
            AlertDFragment alertdFragment = new AlertDFragment();
            // Show Alert DialogFragment
            alertdFragment.show(fm, "Alert Dialog Fragment");

        }
    }
}
