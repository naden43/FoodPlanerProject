package com.example.food_planer.favourate.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_planer.R;
import com.example.food_planer.favourate.presenter.Presenter;
import com.example.food_planer.login.view.LoginActivity;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.IngredentAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Favourate extends Fragment implements IFavourate {


    ConstraintLayout loginMessage;
    ConstraintLayout page ;

    Presenter presenter;
    FavourateAdapter favourateAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourate, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        page = view.findViewById(R.id.page);
        loginMessage = view.findViewById(R.id.loginMessage);

        RecyclerView recyclerView = view.findViewById(R.id.favourateList);

        favourateAdapter = new FavourateAdapter(new ArrayList<>(),getContext());
        LinearLayoutManager ingredentslayoutManager = new LinearLayoutManager(getContext());
        ingredentslayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(ingredentslayoutManager);
        recyclerView.setAdapter(favourateAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(LoginActivity.FILENAME , Context.MODE_PRIVATE);
        presenter = new Presenter(LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(sharedPreferences), FireBaseAuth.getInstance(null)), Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext())),this);

        presenter.getUserMode();





    }

    @Override
    public void showLoginOrRegisterMsg() {
        page.setVisibility(View.GONE);
        loginMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserContent() {

        page.setVisibility(View.VISIBLE);
        loginMessage.setVisibility(View.GONE);

        Flowable<List<MealDetail>> favourets =  presenter.getFavourates();

        favourets.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> favourateAdapter.setList((ArrayList<MealDetail>) item)
                );
    }
}