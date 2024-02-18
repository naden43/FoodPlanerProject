package com.example.food_planer.home.presenter;

import com.example.food_planer.model.WeekMealDetail;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IPresenter {

    public void getInspirationMeals();

    public void getRandomMeals();

    public String getUserId();

    public Flowable<List<WeekMealDetail>> getAllWeekPlans();

    public  void  saveFavouriteMeals(String userId);

    public void savePlansMeals(String userId);

    public void deleteUser();

    public boolean getUserMode();



}
