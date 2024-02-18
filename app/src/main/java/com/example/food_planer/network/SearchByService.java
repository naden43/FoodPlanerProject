package com.example.food_planer.network;

import com.example.food_planer.model.Meals;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchByService {

    @GET("filter.php")
    public Single<Meals> getMealsByCategory(@Query("c") String CategoryName);

    @GET("filter.php")
    public Single<Meals> getMealsByCountry(@Query("a") String areaName);

    @GET("filter.php")
    public Single<Meals> getMealsByIngredient(@Query("i") String areaName);


}
