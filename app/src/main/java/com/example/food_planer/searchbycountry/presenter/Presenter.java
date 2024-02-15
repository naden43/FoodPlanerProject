package com.example.food_planer.searchbycountry.presenter;

import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.network.SearchMealsCallBack;
import com.example.food_planer.searchbycountry.view.ICountryFragment;

public class Presenter implements SearchMealsCallBack , IPresenter {
    Reposatory reposatory;
    ICountryFragment view ;

    public Presenter(Reposatory reposatory , ICountryFragment view){
        this.reposatory = reposatory ;
        this.view = view;
    }
    @Override
    public void onSuccess(Meals meals) {
        view.showMealsData(meals);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }

    @Override
    public void getMealsByCountry(String countryName) {
        reposatory.makeAMealsByCountryCall(this ,countryName);
    }
}
