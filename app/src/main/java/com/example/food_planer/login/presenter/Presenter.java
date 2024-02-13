package com.example.food_planer.login.presenter;

import com.example.food_planer.login.view.ILoginActivity;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.network.NetworkCallback;

public class Presenter implements IPresenter , NetworkCallback {

    LoginAndRegisterReposatory repo ;

    ILoginActivity view ;

    String email ;
    String password;
    public Presenter(LoginAndRegisterReposatory repo , ILoginActivity view )
    {
        this.repo = repo;
        this.view = view;
    }


    @Override
    public void signIn(String email, String password,boolean remember ) {

        if(email.isEmpty()){
            view.showEmailNeeded();
        }
        else if(password.isEmpty()){
            view.showPasswordNeeded();
        }
        else {
            this.email = email;
            this.password = password;
            repo.makeAddCall(email ,password ,  remember, this);
        }
    }

    @Override
    public void addToSharedPrerefrence(String email, String password) {
            repo.addUser(email , password);
    }

    @Override
    public void checkSavedLogin() {
        boolean result = repo.checkSavedAccount();
        if(result)
        {
          view.goToHomePage();
        }
    }

    @Override
    public void successCall(boolean remember) {
        view.showSuccess();
        if(remember) {
            repo.addUser(email, password);
        }
    }
    @Override
    public void FailCall(String errorMsg) {

        view.showFaildLoginMsg(errorMsg);
    }
}
