package com.example.food_planer.model;

import java.util.ArrayList;

public interface PlanMealLocalDataSource {

    public void insertMeal(WeekMealDetail weekMealDetail);

    public void deleteMeal(WeekMealDetail weekMealDetail);

    public void getDayMeal(int day , int month , int year , WeekMealsDelegate weekMealsDelegate);

}
