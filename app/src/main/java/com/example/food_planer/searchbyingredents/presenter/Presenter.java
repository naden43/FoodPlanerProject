package com.example.food_planer.searchbyingredents.presenter;

import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.meals;
import com.example.food_planer.network.SearchMealsCallBack;
import com.example.food_planer.searchbycountry.view.ICountryFragment;
import com.example.food_planer.searchbyingredents.view.IIngredentsMealFragment;

public class Presenter implements IPresenter , SearchMealsCallBack {
    Reposatory reposatory;
    IIngredentsMealFragment view ;

    public Presenter(Reposatory reposatory , IIngredentsMealFragment view){
        this.reposatory = reposatory ;
        this.view = view;
    }
    @Override
    public void onSuccess(meals meals) {
        view.showMealsData(meals);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }

    @Override
    public void getMealsByIngredient(String ingredientName) {
        reposatory.makeAMealsByIngredientCall(this ,ingredientName);
    }
}
