package com.example.food_planer.register.presenter;

public interface IPresenter{
    public void signUp(String email , String password ,String confirmpassword );

    public void addToSharedPrefrence(String email , String password);


}