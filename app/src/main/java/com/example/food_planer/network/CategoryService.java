package com.example.food_planer.network;

import com.example.food_planer.model.Categories;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {


    @GET("categories.php")
    public Single<Categories> getAllCategories();
}
