package com.example.food_planer.searchbycategory.view;

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
import com.example.food_planer.searchbycategory.presenter.Presenter;

import java.util.ArrayList;


public class CategoryMealsFragment extends Fragment implements ICategoryFragment {


    Presenter presenter;

    SearchMealsAdapter searchMealsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_category_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.categoryMeals);

        searchMealsAdapter = new SearchMealsAdapter(new ArrayList<>(),getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchMealsAdapter);

        String strCategory = CategoryMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();


        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance()),this);
        presenter.getMealsByCategory(strCategory);

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