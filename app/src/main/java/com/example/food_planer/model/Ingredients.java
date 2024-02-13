package com.example.food_planer.model;

import java.util.ArrayList;

public class Ingredients {

    ArrayList<Ingredien> meals;

    public Ingredients(ArrayList<Ingredien> meals) {
        this.meals = meals;
    }

    public ArrayList<Ingredien> getIngredents() {
        return meals;
    }

    public void setMeals(ArrayList<Ingredien> meals) {
        this.meals = meals;
    }
}
