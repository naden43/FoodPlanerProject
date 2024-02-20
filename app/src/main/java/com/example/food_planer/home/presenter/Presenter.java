package com.example.food_planer.home.presenter;

import com.example.food_planer.home.view.Ihome;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.InspirationMealNetworkCallBack;
import com.example.food_planer.network.MealCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Presenter implements IPresenter , InspirationMealNetworkCallBack , MealCallBack {



    Reposatory repo ;

    LoginAndRegisterReposatory logoutRepo ;
    Ihome view ;

    public Presenter(Reposatory repo , Ihome view , LoginAndRegisterReposatory logoutRepo){
        this.repo = repo;
        this.view = view;
        this.logoutRepo = logoutRepo;
    }

    @Override
    public void getInspirationMeals() {
        for(int i=0 ;i<2 ;i++) {
            repo.makeAInspirationMealCall(this);
        }
    }

    @Override
    public void getRandomMeals() {
        for(int i=0 ;i<2 ;i++) {
            repo.makeARandomMealCall(this);
        }
    }

    @Override
    public String getUserId() {
        return logoutRepo.getUserId();
    }

    @Override
    public Flowable<List<WeekMealDetail>> getAllWeekPlans() {
        return repo.getAllWeekPlans();
    }

    Disposable favoriteDisposable;
    @Override
    public void saveFavouriteMeals(String userId) {

        Flowable<List<MealDetail>> favourites = repo.getFavourates();
        favoriteDisposable = favourites.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        favouriteList -> {
                                            repo.saveFavouriteMeals((ArrayList<MealDetail>) favouriteList, userId);
                                            favoriteDisposable.dispose();
                                            delegateFavouriteFinish((ArrayList<MealDetail>) favouriteList);
                                        }
                                );

    }

    Disposable Plansdisposable;
    @Override
    public void savePlansMeals(String userId) {
        Flowable<List<WeekMealDetail>> plans = repo.getAllWeekPlans();
        Plansdisposable = plans.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plansList -> {
                            repo.savePlansMeal((ArrayList<WeekMealDetail>) plansList, userId);
                            Plansdisposable.dispose();
                            delegatePlansFinish((ArrayList<WeekMealDetail>) plansList);

                        }
                );

    }

    @Override
    public void deleteUser() {
        logoutRepo.deleteUser();
    }

    @Override
    public boolean getUserMode() {
        return logoutRepo.checkSavedAccount();
    }

    public void delegatePlansFinish(ArrayList<WeekMealDetail> weekMealDetails)
    {
        //disposable.dispose();
        for(int i=0 ;i<weekMealDetails.size();i++)
        {
            repo.deletePlanMeal(weekMealDetails.get(i));
        }

    }


    public void delegateFavouriteFinish(ArrayList<MealDetail> mealDetails)
    {
        for(int i=0 ;i<mealDetails.size();i++){
            repo.removeFavourate(mealDetails.get(0));
        }
    }



    @Override
    public void onSuccess(Meals meal) {
        view.showData(meal.getMeals().get(0));
    }

    @Override
    public void onSuccess(Meal meal) {
        view.showRandomData(meal);
    }

    @Override
    public void onFailure(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }

    public Flowable<List<MealDetail>> getAllFavourate()
    {
        return repo.getFavourates();
    }
}
