package com.example.food_planer.network;

import com.example.food_planer.model.Meal;

public interface RandomMealCallBack {

    public void onSuccess(Meal meal);

    public void onFailure(String errorMsg);
}
