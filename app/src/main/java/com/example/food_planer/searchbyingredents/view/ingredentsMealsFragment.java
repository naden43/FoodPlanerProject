package com.example.food_planer.searchbyingredents.view;

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
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.SearchMealsAdapter;
import com.example.food_planer.searchbyingredents.presenter.Presenter;

import java.util.ArrayList;

public class ingredentsMealsFragment extends Fragment implements IIngredentsMealFragment {


    public static final int INGREDIENT_MEALS = 3;
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
        return inflater.inflate(R.layout.fragment_ingredents_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.ingredientsMeals);

        searchMealsAdapter = new SearchMealsAdapter(new ArrayList<>(),getContext(),INGREDIENT_MEALS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchMealsAdapter);

        String strIngredient = ingredentsMealsFragmentArgs.fromBundle(getArguments()).getStrIgredent();


        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext())),this);
        presenter.getMealsByIngredient(strIngredient);
    }
    @Override
    public void showMealsData(Meals meals) {
        searchMealsAdapter.setList(meals.getMeals());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}