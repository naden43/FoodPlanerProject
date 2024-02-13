package com.example.food_planer.search.presenter;

import com.example.food_planer.model.Categories;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.CategoriesNetworkCallBack;
import com.example.food_planer.network.CountriesNetworkCallBack;
import com.example.food_planer.network.IngredientNetworkCallBack;
import com.example.food_planer.search.view.Isearch;

public class Presenter implements IPresenter , CategoriesNetworkCallBack , CountriesNetworkCallBack , IngredientNetworkCallBack {


    Reposatory repo ;
    Isearch view ;

    public Presenter(Reposatory repo , Isearch view) {
        this.repo = repo ;
        this.view = view;
    }


    @Override
    public void getAllCategories() {
        repo.makeACategoriesCall(this);
    }

    @Override
    public void getAllCountries() {
        repo.makeACountriesCall(this);
    }

    @Override
    public void getAllIngredents() {
        repo.makeAIngredentCall(this);
    }

    @Override
    public void onSuccess(Categories categories) {
        view.showCategoriesData(categories);
    }

    @Override
    public void onSuccess(Countries countries) {
        view.showCountriesData(countries);
    }

    @Override
    public void onSuccess(Ingredients ingredients) {
        view.showIngredentsData(ingredients);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showError(errorMsg);
    }
}
