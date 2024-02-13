package com.example.food_planer.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.presenter.Presenter;
import com.example.food_planer.model.Categories;

import java.util.ArrayList;


public class Search extends Fragment implements Isearch {


    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;

    IngredentAdapter ingredentAdapter;

    Presenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewCategories;
        recyclerViewCategories = view.findViewById(R.id.CategoryList);

        RecyclerView recyclerViewCountries;
        recyclerViewCountries = view.findViewById(R.id.CountryList);

        RecyclerView recyclerViewIngredents;
        recyclerViewIngredents= view.findViewById(R.id.IngredentList);

        categoryAdapter = new CategoryAdapter(new ArrayList<>(),getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setAdapter(categoryAdapter);

        countryAdapter = new CountryAdapter(new ArrayList<>(),getContext());
        LinearLayoutManager countrylayoutManager = new LinearLayoutManager(getContext());
        countrylayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCountries.setLayoutManager(countrylayoutManager);
        recyclerViewCountries.setAdapter(countryAdapter);


        ingredentAdapter = new IngredentAdapter(new ArrayList<>(),getContext());
        LinearLayoutManager ingredentslayoutManager = new LinearLayoutManager(getContext());
        ingredentslayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewIngredents.setLayoutManager(ingredentslayoutManager);
        recyclerViewIngredents.setAdapter(ingredentAdapter);



        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance()),this);

        presenter.getAllCategories();
        presenter.getAllCountries();
        presenter.getAllIngredents();



    }

    @Override
    public void showCategoriesData(Categories categories) {
        categoryAdapter.setList(categories.getCategories());
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCountriesData(Countries countries) {
        countryAdapter.setList(countries.getCountries());
    }

    @Override
    public void showIngredentsData(Ingredients ingredients) {
        ingredentAdapter.setList(ingredients.getIngredents());
    }
}