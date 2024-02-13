package com.example.food_planer.network;

import com.example.food_planer.model.meals;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomMealService {

    @GET("random.php")
    public Call<meals> getRandomMeal();
}
