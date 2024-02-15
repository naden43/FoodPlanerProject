package com.example.food_planer.model;

import android.content.Context;

import com.example.food_planer.db.MealDao;
import com.example.food_planer.db.RoomDataBase;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSourceimpl implements MealLocalDataSource{

    private static MealLocalDataSourceimpl instance = null ;

    RoomDataBase db ;
    private Flowable<List<MealDetail>> favourates;

    MealLocalDataSourceimpl remote;
    private MealLocalDataSourceimpl(Context context)
    {
        db = RoomDataBase.getInstance(context);
        MealDao mealDao = db.getMealDao();
        favourates = mealDao.getAllFavourateMeals();

    }

    @Override
    public Flowable<List<MealDetail>> getFavourates() {

        return favourates;
    }

    @Override
    public void removeFavourate(MealDetail mealDetail) {

        new Thread()
        {
            @Override
            public void run() {
                db.getMealDao().delete(mealDetail);
            }
        }.start();
    }

    @Override
    public void addToFavourate(MealDetail mealDetail){
        new Thread()
        {
            @Override
            public void run() {

                db.getMealDao().insert(mealDetail);
            }
        }.start();
    }

    public static MealLocalDataSourceimpl getInstance(Context context){
        if(instance==null)
        {
            instance = new MealLocalDataSourceimpl(context);
        }

        return instance;
    }

}
