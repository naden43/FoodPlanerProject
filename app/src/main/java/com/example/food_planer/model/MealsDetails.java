package com.example.food_planer.model;

import java.util.ArrayList;

public class MealsDetails {

    ArrayList<MealDetail> meals;

    public MealsDetails(ArrayList<MealDetail> meals) {
        this.meals = meals;
    }

    public ArrayList<MealDetail> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<MealDetail> meals) {
        this.meals = meals;
    }
}
