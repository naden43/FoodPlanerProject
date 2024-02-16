package com.example.food_planer.model;

import android.content.Context;

import com.example.food_planer.db.MealDao;
import com.example.food_planer.db.RoomDataBase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    @Override
    public void getMeal(String strMeal, DataBaseDelegate dataBaseDelegate) {
        Observable.create(
                item -> item.onNext(db.getMealDao().getMeal(strMeal))
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> dataBaseDelegate.onSuccess((MealDetail) item)
                );

    }

    public static MealLocalDataSourceimpl getInstance(Context context){
        if(instance==null)
        {
            instance = new MealLocalDataSourceimpl(context);
        }

        return instance;
    }

}
