package com.example.food_planer.searchbyingredents.view;

import com.example.food_planer.model.Meals;

public interface IIngredentsMealFragment {
    public void showMealsData(Meals meals);

    public void showErrorMsg(String errorMsg);
}
