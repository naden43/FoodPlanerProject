package com.example.food_planer.network;

import com.example.food_planer.model.meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchByService {

    @GET("filter.php")
    public Call<meals> getMealsByCategory(@Query("c") String CategoryName);

    @GET("filter.php")
    public Call<meals> getMealsByCountry(@Query("a") String areaName);

    @GET("filter.php")
    public Call<meals> getMealsByIngredient(@Query("i") String areaName);


}
