package com.example.food_planer.searchbycategory.view;

import com.example.food_planer.model.Meals;

public interface ICategoryFragment {

    public void showMealsData(Meals meals);

    public void showErrorMsg(String errorMsg);
}
