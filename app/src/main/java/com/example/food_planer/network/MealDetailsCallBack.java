package com.example.food_planer.network;

import com.example.food_planer.model.MealDetail;

public interface MealDetailsCallBack {
    public void onSuccess(MealDetail meal);

    public void onFailure(String errorMsg);
}
