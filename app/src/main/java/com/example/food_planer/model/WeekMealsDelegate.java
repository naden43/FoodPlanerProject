package com.example.food_planer.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface WeekMealsDelegate {

    public void onSuccess(ArrayList<WeekMealDetail> weekMealDetails);
}
