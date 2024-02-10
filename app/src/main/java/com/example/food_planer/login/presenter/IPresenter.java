package com.example.food_planer.login.presenter;

import java.util.SplittableRandom;

public interface IPresenter {

    public void signIn(String email , String password , boolean remember);

    public void addToSharedPrerefrence(String email , String password);

    public void checkSavedLogin();
}
