package com.example.food_planer.login.view;

public interface ILoginActivity {

    public void showEmailNeeded();

    public void showPasswordNeeded();

    public void showFaildLoginMsg(String errorMsg);

    public void showSuccess();

    public void goToHomePage();


}
