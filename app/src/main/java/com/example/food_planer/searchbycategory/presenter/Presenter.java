package com.example.food_planer.searchbycategory.presenter;

import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.network.SearchMealsCallBack;
import com.example.food_planer.searchbycategory.view.ICategoryFragment;

public class Presenter implements IPresenter , SearchMealsCallBack {

    Reposatory reposatory;
    ICategoryFragment view ;

    public Presenter(Reposatory reposatory , ICategoryFragment view){
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
    public void getMealsByCategory(String categoryName) {
        reposatory.makeAMealByCategoryCall(this ,categoryName);
    }
}
