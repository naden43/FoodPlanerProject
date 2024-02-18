package com.example.food_planer.network;

import com.example.food_planer.model.Ingredients;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientServices {

    @GET("list.php?i=list")
    public Single<Ingredients> getAllIngredents();
}
