package com.example.food_planer.dpfirestore;

import com.example.food_planer.model.DataBaseDelegate;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.FireBaseResponse;

import java.util.ArrayList;

public interface FireStoreRemoteDataSource {
    void saveFavouriteMeals(ArrayList<MealDetail> favoriteMeals, String userId);

    void savePlanMeals(ArrayList<WeekMealDetail> planMeals, String userId);

    public void getSavedMeals(String userId, FireBaseResponse fireBaseResponse);
}
