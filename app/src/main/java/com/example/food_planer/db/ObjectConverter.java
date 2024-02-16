package com.example.food_planer.db;

import androidx.room.TypeConverter;


import com.example.food_planer.model.MealDetail;
import com.google.gson.Gson;

public class ObjectConverter {

        @TypeConverter
        public String fromMealToString(MealDetail meal) {
            return new Gson().toJson(meal);
        }

        @TypeConverter
        public MealDetail fromStringToMeal(String string) {
            return new Gson().fromJson(string, MealDetail.class);
        }
}
