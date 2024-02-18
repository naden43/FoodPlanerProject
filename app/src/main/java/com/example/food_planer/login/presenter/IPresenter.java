package com.example.food_planer.login.presenter;

import com.example.food_planer.network.NetworkCallback;

import java.util.SplittableRandom;

public interface IPresenter {

    public void signIn(String email , String password , boolean remember);

    public void checkSavedLogin();

    public void sigInByGoogle();
    public void intializeLauncher();
    public boolean checkConnectivity();

}
