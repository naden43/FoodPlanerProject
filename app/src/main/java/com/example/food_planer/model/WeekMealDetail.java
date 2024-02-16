package com.example.food_planer.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.ArrayList;

@Entity(tableName = "week_meals" , primaryKeys = {"strMeal", "Day" , "month"  , "year"})
public class WeekMealDetail {

    @NonNull
    String strMeal;

    public MealDetail getMealDetail() {
        return mealDetail;
    }

    public void setMealDetail(MealDetail mealDetail) {
        this.mealDetail = mealDetail;
    }

    @NonNull
    @ColumnInfo(name = "Day")
    public int Day;


    @ColumnInfo(name = "mealDetail")
    public MealDetail mealDetail;


    public WeekMealDetail(@NonNull String strMeal, int day, MealDetail mealDetail, int month, int year) {
        this.strMeal = strMeal;
        Day = day;
        this.mealDetail = mealDetail;
        this.month = month;
        this.year = year;
    }
    public WeekMealDetail()
    {

    }

    @NonNull
    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(@NonNull String strMeal) {
        this.strMeal = strMeal;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    @ColumnInfo(name = "month")
    public int month;

    @NonNull
    @ColumnInfo(name = "year")
    public int year;

}



