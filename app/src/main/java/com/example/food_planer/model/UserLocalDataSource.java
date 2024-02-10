package com.example.food_planer.model;

public interface UserLocalDataSource {

    public boolean checkSavedLogin();

    public void addCredintialsTOfile(String email , String password);
}
