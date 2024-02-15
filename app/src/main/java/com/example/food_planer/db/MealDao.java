package com.example.food_planer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.food_planer.mealpage.view.MealDetails;
import com.example.food_planer.model.MealDetail;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {

    @Query("SELECT * FROM favourate_meals")
    Flowable<List<MealDetail>> getAllFavourateMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(MealDetail mealDetail);

    @Delete
    public void delete(MealDetail mealDetail);
}
