package com.example.food_planer.home.view;

import com.example.food_planer.model.Meal;

public interface Ihome {

    public void showData(Meal meal);

    public void showErrorMsg(String errorMsg);

    public void showRandomData(Meal meal);
}
