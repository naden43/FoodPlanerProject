package com.example.food_planer.network;

import com.example.food_planer.model.Countries;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService {


    @GET("list.php?a=list")
    public Single<Countries> getAllCountries();
}
