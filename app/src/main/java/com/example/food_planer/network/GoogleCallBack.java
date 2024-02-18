package com.example.food_planer.network;

public interface GoogleCallBack {

    public void successSignIn(String email , String userId);

    public void FailCall(String errorMsg);
}
