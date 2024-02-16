package com.example.food_planer.mealpage.presenter;

import com.example.food_planer.mealpage.view.IMealDetails;
import com.example.food_planer.model.DataBaseDelegate;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.MealDetailsCallBack;

import java.util.ArrayList;

public class Presenter implements IPresenter , MealDetailsCallBack , DataBaseDelegate {

    Reposatory repo;
    IMealDetails view ;

    public Presenter(Reposatory repo, IMealDetails view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getMealData(String strMeal) {
        repo.makeAMealsDetailsCall(this , strMeal);
    }

    @Override
    public void getLocalMealData(String strMeal) {
         repo.getLocalMeal(strMeal ,this);
    }

    @Override
    public void addToFavourate(MealDetail mealDetail) {
        repo.addToFavourate(mealDetail);
    }

    @Override
    public void addToPlan(WeekMealDetail weekMealDetail) {
        repo.addToPlan(weekMealDetail);
    }

    @Override
    public void onSuccess(MealDetail meal) {
        view.showMealData(meal);
    }

    @Override
    public void onSuccess(ArrayList<MealDetail> mealDetails) {

    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }
}
