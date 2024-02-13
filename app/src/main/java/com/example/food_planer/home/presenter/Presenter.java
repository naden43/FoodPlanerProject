package com.example.food_planer.home.presenter;

import com.example.food_planer.home.view.Ihome;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.meals;
import com.example.food_planer.network.InspirationMealNetworkCallBack;
import com.example.food_planer.network.RandomMealCallBack;

public class Presenter implements IPresenter , InspirationMealNetworkCallBack , RandomMealCallBack {



    Reposatory repo ;

    Ihome view ;

    public Presenter(Reposatory repo , Ihome view){
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getInspirationMeals() {
        for(int i=0 ;i<10 ;i++) {
            repo.makeAInspirationMealCall(this);
        }
    }

    @Override
    public void getRandomMeals() {
        for(int i=0 ;i<20 ;i++) {
            repo.makeARandomMealCall(this);
        }
    }

    @Override
    public void onSuccess(meals meal) {
        view.showData(meal.getMeals().get(0));
    }

    @Override
    public void onSuccess(Meal meal) {
            view.showRandomData(meal);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }
}
