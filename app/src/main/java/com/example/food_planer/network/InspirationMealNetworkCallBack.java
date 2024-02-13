package com.example.food_planer.network;

import com.example.food_planer.model.Meal;
import com.example.food_planer.model.meals;

public interface InspirationMealNetworkCallBack {

    public void onSuccess(meals meal);

    public void onFailure(String errorMsg);

}
