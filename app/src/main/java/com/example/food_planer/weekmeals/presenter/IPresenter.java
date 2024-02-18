package com.example.food_planer.weekmeals.presenter;

import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;

public interface IPresenter {

    public void getWeekMeal(int day  , int month , int year);

    public void deletePlanMeal(WeekMealDetail weekMealDetail);

    public void stopSubscribe();


    }
