package com.example.food_planer.searchbycategory.view;

import com.example.food_planer.model.meals;

public interface ICategoryFragment {

    public void showMealsData(meals meals);

    public void showErrorMsg(String errorMsg);
}
