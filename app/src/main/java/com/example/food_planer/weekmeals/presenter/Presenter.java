package com.example.food_planer.weekmeals.presenter;

import com.example.food_planer.model.DataBaseDelegate;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;
import com.example.food_planer.weekmeals.view.IWeekMeals;

import java.util.ArrayList;

public class Presenter implements IPresenter ,WeekMealsDelegate{

    Reposatory repo ;
    IWeekMeals view ;
    LoginAndRegisterReposatory reposatory;

    public Presenter(Reposatory repo , IWeekMeals view , LoginAndRegisterReposatory reposatory){
        this.repo = repo;
        this.view = view;
        this.reposatory = reposatory;
    }
    @Override
    public void getWeekMeal(int day, int month, int year) {
        repo.getWeekMeals(day , month , year ,this);
    }

    @Override
    public void getUserMode() {
        if(reposatory.checkSavedAccount()){
            view.showUserContent();
        }
        else {
            view.showLoginOrRegisterMsg();
        }
    }

    @Override
    public void deletePlanMeal(WeekMealDetail weekMealDetail) {
        repo.deletePlanMeal(weekMealDetail);
    }

    @Override
    public void stopSubscribe() {
        repo.stopSubscribe();
    }

    @Override
    public void onSuccess(ArrayList<WeekMealDetail> weekMealDetails) {
        view.showMeals(weekMealDetails);
    }

}
