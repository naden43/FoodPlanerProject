package com.example.food_planer.searchbyingredents.view;

import com.example.food_planer.model.meals;

public interface IIngredentsMealFragment {
    public void showMealsData(meals meals);

    public void showErrorMsg(String errorMsg);
}
