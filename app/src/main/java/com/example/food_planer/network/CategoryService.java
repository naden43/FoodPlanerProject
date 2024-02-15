package com.example.food_planer.network;

import com.example.food_planer.model.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {


    @GET("list.php?c=list")
    public Call<Categories> getAllCategories();
}
