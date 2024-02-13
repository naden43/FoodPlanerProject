package com.example.food_planer.searchbycountry.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.meals;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.SearchMealsAdapter;
import com.example.food_planer.searchbycountry.presenter.Presenter;

import java.util.ArrayList;

public class CountriesMealFragment extends Fragment implements ICountryFragment {

    SearchMealsAdapter searchMealsAdapter;

    Presenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.countriesMeals);

        searchMealsAdapter = new SearchMealsAdapter(new ArrayList<>(),getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchMealsAdapter);

        String strCountry = CountriesMealFragmentArgs.fromBundle(getArguments()).getStrCountry();


        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance()),this);
        presenter.getMealsByCountry(strCountry);
    }

    @Override
    public void showMealsData(meals meals) {
        searchMealsAdapter.setList(meals.getMeals());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}