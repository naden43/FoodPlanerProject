package com.example.food_planer.register.view;

public interface IRegisterActivity {

    public void showEmailNeeded();

    public void showPasswordNeeded();

    public void showConfirmationPasswordNeeded();

    public void showEqualPasswords();

    public void showFailedLoginMsg(String errorMsg);

    public void showSuccess();


}
