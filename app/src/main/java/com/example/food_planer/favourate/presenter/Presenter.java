package com.example.food_planer.favourate.presenter;

import com.example.food_planer.favourate.view.IFavourate;
import com.example.food_planer.model.LoginAndRegisterReposatory;

public class Presenter implements IPresenter{

    LoginAndRegisterReposatory repo;
    IFavourate view ;

    public Presenter( LoginAndRegisterReposatory repo , IFavourate view ){
        this.repo = repo;
        this.view =view;
    }
    @Override
    public void getUserMode() {
        if(repo.checkSavedAccount()){
            view.showUserContent();
        }
        else {
            view.showLoginOrRegisterMsg();
        }
    }
}
