package com.example.food_planer.network;

import com.example.food_planer.model.Ingredients;

public interface IngredientNetworkCallBack {

    public void onSuccess(Ingredients ingredients);

    public void onFailure(String errorMsg);
}
