package com.example.food_planer.model;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MealLocalDataSource {
    Flowable<List<MealDetail>> getFavourates();

    void removeFavourate(MealDetail favourate);

    void addToFavourate(MealDetail MealDetail);

}
