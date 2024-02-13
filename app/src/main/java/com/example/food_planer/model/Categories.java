package com.example.food_planer.model;

import java.util.ArrayList;

public class Categories {

    ArrayList<Category> meals ;

    public Categories(ArrayList<Category> meals) {
        this.meals = meals;
    }

    public ArrayList<Category> getCategories() {
        return meals;
    }

    public void setMeals(ArrayList<Category> meals) {
        this.meals = meals;
    }
}
