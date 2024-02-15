package com.example.food_planer.model;

import com.example.food_planer.network.CategoriesNetworkCallBack;
import com.example.food_planer.network.CountriesNetworkCallBack;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.network.IngredientNetworkCallBack;
import com.example.food_planer.network.InspirationMealNetworkCallBack;
import com.example.food_planer.network.MealCallBack;
import com.example.food_planer.network.MealDetailsCallBack;
import com.example.food_planer.network.SearchMealsCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class Reposatory {

    private static Reposatory instance = null;



    FoodRemoteSourceImpl foodRemoteSource;
    MealLocalDataSource mealLocalDataSource;

    private Reposatory(FoodRemoteSourceImpl foodRemoteSource ,MealLocalDataSource mealLocalDataSource)
    {

        this.foodRemoteSource = foodRemoteSource;

        this.mealLocalDataSource = mealLocalDataSource;
    }

    static public Reposatory getInstance(FoodRemoteSourceImpl foodRemoteSource , MealLocalDataSource mealLocalDataSource)
    {
        if(instance==null)
        {
            instance = new Reposatory(foodRemoteSource,mealLocalDataSource);
        }
        return instance;
    }

    public void makeAInspirationMealCall(InspirationMealNetworkCallBack inspirationMealNetworkCallBack)
    {
        foodRemoteSource.makeAInspirationMealCall(inspirationMealNetworkCallBack);
    }

   public void makeACategoriesCall(CategoriesNetworkCallBack  categoriesNetworkCallBack)
    {
        foodRemoteSource.makeACategoiresCall(categoriesNetworkCallBack);
    }
    public void makeACountriesCall(CountriesNetworkCallBack countriesNetworkCallBack)
    {
        foodRemoteSource.makeACountriesCall(countriesNetworkCallBack);
    }

    public void makeAIngredentCall(IngredientNetworkCallBack ingredientNetworkCallBack)
    {
        foodRemoteSource.makeAIngredientsCall(ingredientNetworkCallBack);
    }

    public void makeARandomMealCall(MealCallBack mealCallBack){
        foodRemoteSource.makeARandomMealCall(mealCallBack);
    }

    public void makeAMealByCategoryCall(SearchMealsCallBack searchMealsCallBack , String CategoryName){
        foodRemoteSource.makeAMealsByCategoryCall(searchMealsCallBack , CategoryName);
    }

    public void makeAMealsByCountryCall(SearchMealsCallBack searchMealsCallBack , String countryName){
        foodRemoteSource.makeAMealByCountryCall(searchMealsCallBack ,countryName);
    }

    public void makeAMealsByIngredientCall(SearchMealsCallBack searchMealsCallBack , String ingredientName){
        foodRemoteSource.makeAMealsByIngredientCall(searchMealsCallBack ,ingredientName);
    }


    public void  makeAMealsDetailsCall(MealDetailsCallBack mealDetailsCallBack , String strMeal){
        foodRemoteSource.makeAMealDetailsCall(mealDetailsCallBack , strMeal);
    }

    public Flowable<List<MealDetail>> getFavourates() {

        return mealLocalDataSource.getFavourates();
    }


    public void removeFavourate(MealDetail favourate) {

        mealLocalDataSource.removeFavourate(favourate);
    }


    public void addToFavourate(MealDetail favourate){
        mealLocalDataSource.addToFavourate(favourate);
    }

}
