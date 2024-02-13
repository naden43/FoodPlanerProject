package com.example.food_planer.network;

import com.example.food_planer.model.Categories;

public interface CategoriesNetworkCallBack {

    public void onSuccess(Categories categories);

    public void onFailure(String errorMsg);
}
