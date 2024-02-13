package com.example.food_planer.network;

import com.example.food_planer.model.Countries;

public interface CountriesNetworkCallBack {

    public void onSuccess(Countries countries);

    public void onFailure(String errorMsg);
}
