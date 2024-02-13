package com.example.food_planer.model;

public class Meal {

     String strMeal;
     String strCategory;
     String strArea;
     String strMealThumb;
     public String getStrMealThumb() {
        return strMealThumb;
    }

     public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

     public Meal(String strMeal, String strCategory, String strArea, String strMealThumb) {
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strMealThumb = strMealThumb;
     }

     public String getStrMeal() {
        return strMeal;
    }

     public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

     public String getStrCategory() {
        return strCategory;
    }

     public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

     public String getStrArea() {
        return strArea;
    }

     public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
