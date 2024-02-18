package com.example.food_planer.weekmeals.view;

import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;

import java.util.ArrayList;

public interface IWeekMeals {

    public void showMeals(ArrayList<WeekMealDetail> weekMealDetails);

    public void showLoginOrRegisterMsg();

    public void showUserContent();
}
