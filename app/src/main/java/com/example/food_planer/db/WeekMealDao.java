package com.example.food_planer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface WeekMealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(WeekMealDetail mealDetail);
    @Query("SELECT * FROM week_meals WHERE Day = :day and month = :month and year = :year")
    public Flowable<List<WeekMealDetail>> getMeal(int day , int month , int year);

    @Delete
    public void delete(WeekMealDetail weekMealDetail);

    @Query("SELECT * FROM week_meals WHERE Day = :day and month = :month and year = :year and strMeal =:strMeal")
    public WeekMealDetail getSpecificMeal(String strMeal , int day , int month  , int year );

    @Query("SELECT * FROM week_meals")
    public Flowable<List<WeekMealDetail>> getAllPlans();

}
