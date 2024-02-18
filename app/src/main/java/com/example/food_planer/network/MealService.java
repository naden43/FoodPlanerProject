package com.example.food_planer.network;

import com.example.food_planer.model.Meals;
import com.example.food_planer.model.MealsDetails;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("search.php")
    Single<MealsDetails> getFullDetailedMeal(@Query("s") String idMeal);

}
