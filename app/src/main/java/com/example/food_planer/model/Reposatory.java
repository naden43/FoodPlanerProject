package com.example.food_planer.model;

import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.network.CategoriesNetworkCallBack;
import com.example.food_planer.network.CountriesNetworkCallBack;
import com.example.food_planer.network.FireBaseResponse;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.network.IngredientNetworkCallBack;
import com.example.food_planer.network.InspirationMealNetworkCallBack;
import com.example.food_planer.network.MealCallBack;
import com.example.food_planer.network.MealDetailsCallBack;
import com.example.food_planer.network.SearchMealsCallBack;
import com.example.food_planer.weekmeals.view.WeekMealDetailDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class Reposatory {

    private static Reposatory instance = null;



    FoodRemoteSourceImpl foodRemoteSource;
    MealLocalDataSource mealLocalDataSource;

    PlanMealLocalDataSourceimpl planMeal;

    FireStoreRemoteDataSourceimpl fireStoreDb;

    private Reposatory(FoodRemoteSourceImpl foodRemoteSource , MealLocalDataSource mealLocalDataSource , PlanMealLocalDataSourceimpl planMeal , FireStoreRemoteDataSourceimpl fireStoreDb)
    {

        this.foodRemoteSource = foodRemoteSource;
        this.planMeal = planMeal;
        this.mealLocalDataSource = mealLocalDataSource;

        this.fireStoreDb = fireStoreDb;
    }

    static public Reposatory getInstance(FoodRemoteSourceImpl foodRemoteSource , MealLocalDataSource mealLocalDataSource , PlanMealLocalDataSourceimpl planMeal ,FireStoreRemoteDataSourceimpl fireStoreDb)
    {
        if(instance==null)
        {
            instance = new Reposatory(foodRemoteSource,mealLocalDataSource , planMeal,fireStoreDb);
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

    public void getLocalMeal(String strMeal , DataBaseDelegate dataBaseDelegate){
        mealLocalDataSource.getMeal(strMeal ,dataBaseDelegate);
    }

    public void  addToPlan(WeekMealDetail weekMealDetail){planMeal.insertMeal(weekMealDetail);}

    public void getWeekMeals(int day , int month , int year , WeekMealsDelegate weekMealsDelegate){
        planMeal.getDayMeal(day , month , year , weekMealsDelegate);
    }

    public void deletePlanMeal(WeekMealDetail weekMealDetail){
        planMeal.deleteMeal(weekMealDetail);
    }

    public void stopSubscribe()
    {
        planMeal.stopSubscribe();
    }

    public void getSpecificMeal(String strMeal , int day , int month , int year , WeekMealDetailDelegate weekMealDetailDelegate){
        planMeal.getSpecificMeal(strMeal , day , month , year , weekMealDetailDelegate);
    }

    public Flowable<List<WeekMealDetail>> getAllWeekPlans()
    {
       return planMeal.getAllWeekPlans();
    }



    public void saveFavouriteMeals(ArrayList<MealDetail> favourite , String userId)
    {
        fireStoreDb.saveFavouriteMeals(favourite , userId);
    }

    public void savePlansMeal(ArrayList<WeekMealDetail> plans , String userId)
    {
        fireStoreDb.savePlanMeals(plans , userId);
    }

    public void getSavedMeals(String userId , FireBaseResponse fireBaseResponse)
    {
        fireStoreDb.getSavedMeals(userId , fireBaseResponse);
    }

}
