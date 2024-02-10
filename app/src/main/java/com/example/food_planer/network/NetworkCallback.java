package com.example.food_planer.network;

public interface NetworkCallback {

    public void successCall(boolean remember);
    public void FailCall(String errorMsg);

}
