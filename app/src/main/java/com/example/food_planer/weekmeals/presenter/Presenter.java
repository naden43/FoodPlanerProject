package com.example.food_planer.weekmeals.presenter;

import com.example.food_planer.model.DataBaseDelegate;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;
import com.example.food_planer.weekmeals.view.IWeekMeals;

import java.util.ArrayList;

public class Presenter implements IPresenter ,WeekMealsDelegate{

    Reposatory repo ;
    IWeekMeals view ;

    public Presenter(Reposatory repo , IWeekMeals view){
        this.repo = repo;
        this.view = view;
    }
    @Override
    public void getWeekMeal(int day, int month, int year) {
        repo.getWeekMeals(day , month , year ,this);
    }

    @Override
    public void onSuccess(ArrayList<WeekMealDetail> weekMealDetails) {
        view.showMeals(weekMealDetails);
    }
}
