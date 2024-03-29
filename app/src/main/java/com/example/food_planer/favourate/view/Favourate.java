package com.example.food_planer.favourate.view;

import android.content.Intent;
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
import android.widget.Button;

import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.favourate.presenter.Presenter;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.register.view.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Favourate extends Fragment implements IFavourate , deleteSetOnClickListener {


    ConstraintLayout loginMessage;
    ConstraintLayout page ;

    Presenter presenter;
    FavourateAdapter favourateAdapter;

    Button siginUp ;


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

        page = view.findViewById(R.id.pageScroll);
        loginMessage = view.findViewById(R.id.loginMessage);
        siginUp = view.findViewById(R.id.siginUpRedirect);

        siginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , RegisterActivity.class));
                getActivity().finish();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.favourateList);

        favourateAdapter = new FavourateAdapter(new ArrayList<>(),this,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(favourateAdapter);

        presenter = new Presenter(LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(getContext()), FireBaseAuth.getInstance(getActivity())), Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);
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

    @Override
    public void deleteFavourateItem(MealDetail mealDetail) {
        presenter.RemoveFromFavourate(mealDetail);
    }
}