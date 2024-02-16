package com.example.food_planer.mealpage.presenter;

import com.example.food_planer.model.MealDetail;

public interface IPresenter {

    public void getMealData(String strMeal);

    public void getLocalMealData(String strMeal);
    public void addToFavourate(MealDetail mealDetail);
}
