package com.example.kasparsfisers.loginapp;

import android.content.SharedPreferences;

/**
 * Created by kaspars.fisers on 10/18/2016.
 */

public class SharedPreferencewsUtils {

    private static volatile   SharedPreferencewsUtils instance;

    private SharedPreferencewsUtils(){}

    public  static SharedPreferencewsUtils  getInstance(){
        if(instance == null){
            instance = new SharedPreferencewsUtils();
        }
        return instance;
    }


}
