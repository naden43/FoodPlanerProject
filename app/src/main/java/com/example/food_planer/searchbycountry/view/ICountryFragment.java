package com.example.food_planer.searchbycountry.view;

import com.example.food_planer.model.Meals;

public interface ICountryFragment {

    public void showMealsData(Meals meals);

    public void showErrorMsg(String errorMsg);
}
