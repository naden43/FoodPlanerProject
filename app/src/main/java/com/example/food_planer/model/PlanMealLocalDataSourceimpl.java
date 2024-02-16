package com.example.food_planer.model;

import android.content.Context;

import com.example.food_planer.db.MealDao;
import com.example.food_planer.db.RoomDataBase;
import com.example.food_planer.db.WeekMealDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanMealLocalDataSourceimpl implements PlanMealLocalDataSource{


    private static PlanMealLocalDataSourceimpl instance = null ;

    RoomDataBase db ;
    PlanMealLocalDataSourceimpl remote;
    private PlanMealLocalDataSourceimpl(Context context)
    {
        db = RoomDataBase.getInstance(context);

    }
    @Override
    public void insertMeal(WeekMealDetail weekMealDetail) {
        Observable.create(item->
                        db.getWeekMealDao().insert(weekMealDetail)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void deleteMeal(WeekMealDetail weekMealDetail) {
        Observable.create(item->
                        db.getWeekMealDao().delete(weekMealDetail)
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void getDayMeal(int day, int month, int year, WeekMealsDelegate weekMealsDelegate) {
        Observable.create(
                        item -> item.onNext(db.getWeekMealDao().getMeal(day, month, year))
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> weekMealsDelegate.onSuccess((ArrayList<WeekMealDetail>) item)
                );
    }

    public static PlanMealLocalDataSourceimpl getInstance(Context context){
        if(instance==null)
        {
            instance = new PlanMealLocalDataSourceimpl(context);
        }

        return instance;
    }
}
