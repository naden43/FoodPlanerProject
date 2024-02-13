package com.example.food_planer.model;

import java.util.ArrayList;

public class meals {
    public meals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
