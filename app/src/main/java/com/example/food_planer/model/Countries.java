package com.example.food_planer.model;

import java.util.ArrayList;

public class Countries {

    ArrayList<Country> meals;

    public Countries(ArrayList<Country> meals) {
        this.meals = meals;
    }

    public ArrayList<Country> getCountries() {
        return meals;
    }

    public void setMeals(ArrayList<Country> meals) {
        this.meals = meals;
    }
}
