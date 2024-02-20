package com.example.food_planer.model;

import java.util.ArrayList;

public class Categories {

    ArrayList<Category> categories ;

    public Categories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setMeals(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
