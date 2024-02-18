package com.example.food_planer.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface PlanMealLocalDataSource {

    public void insertMeal(WeekMealDetail weekMealDetail);

    public void deleteMeal(WeekMealDetail weekMealDetail);

    public void getDayMeal(int day , int month , int year , WeekMealsDelegate weekMealsDelegate);

    public void stopSubscribe();

    public Flowable<List<WeekMealDetail>> getAllWeekPlans();
}
