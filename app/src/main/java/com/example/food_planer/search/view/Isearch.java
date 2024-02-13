package com.example.food_planer.search.view;

import com.example.food_planer.model.Categories;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;

public interface Isearch {

    public void showCategoriesData(Categories categories);

    public void showError(String errorMsg);

    public void showCountriesData(Countries countries);

    public void showIngredentsData(Ingredients ingredients);

}
