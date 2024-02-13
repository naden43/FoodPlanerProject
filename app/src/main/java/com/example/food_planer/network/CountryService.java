package com.example.food_planer.network;

import com.example.food_planer.model.Countries;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService {


    @GET("list.php?a=list")
    public Call<Countries> getAllCountries();
}
