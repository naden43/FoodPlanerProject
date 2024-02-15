package com.example.food_planer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.food_planer.model.MealDetail;

@Database(entities = {MealDetail.class} , version = 1)
@TypeConverters(BitmapConverter.class)
public abstract class RoomDataBase extends RoomDatabase {
    private static RoomDataBase instance = null ;

    public abstract MealDao getMealDao();

    public static synchronized RoomDataBase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext() , RoomDataBase.class , "mealDB").build();
        }

        return instance;
    }
}
