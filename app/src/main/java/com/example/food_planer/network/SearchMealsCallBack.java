package com.example.food_planer.network;

import com.example.food_planer.model.Meals;

public interface SearchMealsCallBack {

    public void onSuccess(Meals meals);

    public void onFailure(String errorMsg);

}
