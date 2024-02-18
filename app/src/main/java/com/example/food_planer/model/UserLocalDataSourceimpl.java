package com.example.food_planer.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.food_planer.login.view.LoginActivity;

public class UserLocalDataSourceimpl implements UserLocalDataSource{

    private static final String TAG = "UserLocalDataSourceimpl";
    SharedPreferences sharedPreferences ;

    Context context;
    SharedPreferences.Editor editor ;

    private static final String EMAIL = "email";

    private static final String USERID = "userID";
    private static final String PASSWORD = "password";

    private static UserLocalDataSourceimpl instance = null;
    private static final String FILENAME = "rememberFile";

    private UserLocalDataSourceimpl(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FILENAME , Context.MODE_PRIVATE);
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
    public void addCredintialsTOfile(String email, String password , String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL , email);
        editor.putString(PASSWORD, password);
        editor.putString(USERID , userId);
        editor.commit();
        Log.i(TAG, "addCredentialsTOfile: " + "added" + email + password );
    }

    @Override
    public String getUserId() {

        return sharedPreferences.getString(USERID , "0");

    }

    public static UserLocalDataSourceimpl getInstance(Context context){

        if(instance==null)
        {
            instance = new UserLocalDataSourceimpl(context);
        }
        return instance;
    }

    public void deleteUser()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
