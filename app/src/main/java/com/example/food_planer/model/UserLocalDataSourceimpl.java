package com.example.food_planer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.food_planer.login.view.LoginActivity;

public class UserLocalDataSourceimpl implements UserLocalDataSource{

    private static final String TAG = "UserLocalDataSourceimpl";
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static UserLocalDataSourceimpl instance = null;
    private static final String FILENAME = "rememberFile";

    private UserLocalDataSourceimpl(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }
    @Override
    public boolean checkSavedLogin() {

        if(sharedPreferences.contains(EMAIL))
        {
            Log.i(TAG, "checkSavedLogin: " + "true");
        }

        Log.i(TAG, "checkSavedLogin: + false");
        return sharedPreferences.contains(EMAIL);
    }

    @Override
    public void addCredintialsTOfile(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL , email);
        editor.putString(PASSWORD, password);
        editor.commit();
        Log.i(TAG, "addCredentialsTOfile: " + "added" + email + password );
    }

    public static UserLocalDataSourceimpl getInstance(SharedPreferences sharedPreferences){

        if(instance==null)
        {
            instance = new UserLocalDataSourceimpl(sharedPreferences);
        }
        return instance;
    }

}
