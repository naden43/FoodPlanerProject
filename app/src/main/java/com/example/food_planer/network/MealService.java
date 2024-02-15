package com.example.food_planer.network;

import com.example.food_planer.model.Meals;
import com.example.food_planer.model.MealsDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("search.php")
    Call<MealsDetails> getFullDetailedMeal(@Query("s") String idMeal);

}
