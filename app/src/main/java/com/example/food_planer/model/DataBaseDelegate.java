package com.example.food_planer.model;

import java.util.ArrayList;

public interface DataBaseDelegate {

    public void onSuccess(MealDetail mealDetail);
    public void onSuccess(ArrayList<MealDetail> mealDetails);

}
