package com.example.food_planer.network;

import com.example.food_planer.model.meals;

public interface SearchMealsCallBack {

    public void onSuccess(meals meals);

    public void onFailure(String errorMsg);

}
