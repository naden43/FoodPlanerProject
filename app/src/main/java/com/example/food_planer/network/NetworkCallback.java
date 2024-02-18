package com.example.food_planer.network;

public interface NetworkCallback {

    public void successCall(boolean remember ,String userId);
    public void FailCall(String errorMsg);

}
