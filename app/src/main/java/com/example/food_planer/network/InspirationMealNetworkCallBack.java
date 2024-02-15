package com.example.food_planer.network;

import com.example.food_planer.model.Meals;

public interface InspirationMealNetworkCallBack {

    public void onSuccess(Meals meal);

    public void onFailure(String errorMsg);

}
