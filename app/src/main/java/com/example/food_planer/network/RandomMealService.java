package com.example.food_planer.network;

import com.example.food_planer.model.Meals;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomMealService {

    @GET("random.php")
    public Single<Meals> getRandomMeal();
}
