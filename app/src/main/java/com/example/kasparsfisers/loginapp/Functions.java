package com.example.kasparsfisers.loginapp;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {


    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;


        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


}
