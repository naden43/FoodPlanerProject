package com.example.food_planer.favourate.presenter;

import com.example.food_planer.model.MealDetail;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IPresenter {

    public void getUserMode();
    public void RemoveFromFavourate(MealDetail MealDetail);
    public Flowable<List<MealDetail>> getFavourates();
}
