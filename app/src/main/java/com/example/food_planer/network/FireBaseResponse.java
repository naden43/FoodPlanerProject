package com.example.food_planer.network;

import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;

import java.util.ArrayList;

public interface FireBaseResponse {

    public void sendData(ArrayList<WeekMealDetail> plans , ArrayList<MealDetail> favourites);
}
