package com.example.food_planer.mealpage.presenter;

import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;

public interface IPresenter {

    public void getMealData(String strMeal);

    public void getLocalMealData(String strMeal);
    public void addToFavourate(MealDetail mealDetail);

    public void addToPlan(WeekMealDetail weekMealDetail);

    public void getSpecificPlanMeal(String strMeal , int day , int month , int year );

    public boolean getUserMode();
}
