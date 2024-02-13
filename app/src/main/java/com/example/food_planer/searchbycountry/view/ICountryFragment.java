package com.example.food_planer.searchbycountry.view;

import com.example.food_planer.model.meals;

public interface ICountryFragment {

    public void showMealsData(meals meals);

    public void showErrorMsg(String errorMsg);
}
