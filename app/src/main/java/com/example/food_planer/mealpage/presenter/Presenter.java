package com.example.food_planer.mealpage.presenter;

import com.example.food_planer.mealpage.view.IMealDetails;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.MealDetailsCallBack;

public class Presenter implements IPresenter , MealDetailsCallBack {

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
    public void addToFavourate(MealDetail mealDetail) {
        repo.addToFavourate(mealDetail);
    }

    @Override
    public void onSuccess(MealDetail meal) {
        view.showMealData(meal);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }
}
