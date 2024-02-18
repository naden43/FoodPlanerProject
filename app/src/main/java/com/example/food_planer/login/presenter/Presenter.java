package com.example.food_planer.login.presenter;

import android.util.Log;

import com.example.food_planer.login.view.ILoginActivity;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.FireBaseResponse;
import com.example.food_planer.network.GoogleCallBack;
import com.example.food_planer.network.NetworkCallback;

import java.util.ArrayList;

public class Presenter implements IPresenter , NetworkCallback  , GoogleCallBack , FireBaseResponse {

    LoginAndRegisterReposatory repo ;

    Reposatory reposatory;

    ILoginActivity view ;

    String email ;
    String password;
    public Presenter(LoginAndRegisterReposatory repo , ILoginActivity view , Reposatory reposatory )
    {
        this.reposatory = reposatory;
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
            repo.userSignIn(email ,password ,  remember, this);
        }
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
    public void sigInByGoogle() {
        repo.sigInByGoogle(this , this);
    }

    @Override
    public void intializeLauncher() {
        repo.intializeLauncher(this);
    }

    @Override
    public void successCall(boolean remember , String userId) {
        view.showSuccess();
        if(remember) {
            repo.addUser(email, password , userId);
        }
        loadData(userId);
    }

    @Override
    public void successSignIn(String email , String userId) {
        view.showSuccess();
        repo.addUser(email," " , userId);
        loadData(userId);
    }

    @Override
    public void FailCall(String errorMsg) {
        view.showFaildLoginMsg(errorMsg);
    }

    public void loadData(String userId){
        reposatory.getSavedMeals(userId , this);
    }

    @Override
    public void sendData(ArrayList<WeekMealDetail> plans, ArrayList<MealDetail> favourites) {

        if(!plans.isEmpty()) {
            for (int i1 = 0; i1 < plans.size(); i1++) {
                Log.i("TAG", "sendData: " + "here");
                reposatory.addToPlan(plans.get(i1));
            }
        }
        if(!favourites.isEmpty()){
             for(int i2=0 ;i2<favourites.size();i2++)
             {
                Log.i("TAG", "sendData: " + favourites.get(i2));
                reposatory.addToFavourate(favourites.get(i2));
            }
        }

    }
}
