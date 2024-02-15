package com.example.food_planer.network;

import com.example.food_planer.model.Meal;

public interface MealCallBack {

    public void onSuccess(Meal meal);

    public void onFailure(String errorMsg);
}
