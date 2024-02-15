package com.example.food_planer.favourate.presenter;

import com.example.food_planer.favourate.view.IFavourate;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class Presenter implements IPresenter{

    LoginAndRegisterReposatory repo;
    IFavourate view ;

    Reposatory repoData ;

    public Presenter( LoginAndRegisterReposatory repo, Reposatory repoData , IFavourate view ){
        this.repo = repo;
        this.view =view;
        this.repoData = repoData;
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

    @Override
    public void RemoveFromFavourate(MealDetail mealDetail) {
        repoData.removeFavourate(mealDetail);
    }

    @Override
    public Flowable<List<MealDetail>> getFavourates() {
        return repoData.getFavourates();
    }
}
