package com.example.food_planer.model;

import android.content.Context;
import android.util.Log;

import com.airbnb.lottie.L;
import com.example.food_planer.db.MealDao;
import com.example.food_planer.db.RoomDataBase;
import com.example.food_planer.db.WeekMealDao;
import com.example.food_planer.weekmeals.view.WeekMealDetailDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanMealLocalDataSourceimpl implements PlanMealLocalDataSource{


    private static PlanMealLocalDataSourceimpl instance = null ;

    RoomDataBase db ;
    PlanMealLocalDataSourceimpl remote;

    Flowable<List<WeekMealDetail>> result;

    Disposable subscribe;
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
                .subscribe(
                        item -> Log.i("TAG", "insertMeal: ")
                        ,error-> Log.i("TAG", "insertMeal: " + error.getMessage())
                );
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
        /*Observable.create(
                        item -> item.onNext(db.getWeekMealDao().getMeal(day, month, year))
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> weekMealsDelegate.onSuccess((Flowable<List<WeekMealDetail>>) item)
                );*/

        result = db.getWeekMealDao().getMeal(day , month, year);

        if(subscribe!=null){
        subscribe.dispose();}
        subscribe = result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(item -> weekMealsDelegate.onSuccess((ArrayList<WeekMealDetail>) item )
                , error -> Log.i("TAG", "getDayMeal: "+ error)
                ,()-> Log.i("TAG", "getDayMeal: "));

    }

    public static PlanMealLocalDataSourceimpl getInstance(Context context){
        if(instance==null)
        {
            instance = new PlanMealLocalDataSourceimpl(context);
        }

        return instance;
    }

    public void stopSubscribe()
    {
        if(subscribe!=null) {
            subscribe.dispose();
        }
    }

    public void getSpecificMeal(String strMeal , int day , int month , int year , WeekMealDetailDelegate weekMealDetailDelegate){

        Observable.create(
                item -> item.onNext(db.getWeekMealDao().getSpecificMeal(strMeal , day, month  ,year))
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> weekMealDetailDelegate.sendMealDetail((WeekMealDetail) item)
                );

    }

    public Flowable<List<WeekMealDetail>> getAllWeekPlans()
    {
        return db.getWeekMealDao().getAllPlans();
    }
}
