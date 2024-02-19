package com.example.food_planer.register.presenter;

public interface IPresenter{
    public void signUp(String email , String password ,String confirmpassword );

    public void addToSharedPrefrence(String email , String password);

    public void intializeLauncher();

    public void signInByGoogle();

    public void addToShared(String email , String userId);


}
