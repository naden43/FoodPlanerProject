package com.example.food_planer.searchbycountry.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.model.Category;
import com.example.food_planer.model.Country;
import com.example.food_planer.model.Ingredien;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.SearchMealsAdapter;
import com.example.food_planer.searchbycountry.presenter.Presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class CountriesMealFragment extends Fragment implements ICountryFragment {

    private static final int COUNTRY_MEALS = 2;
    SearchMealsAdapter searchMealsAdapter;

    Presenter presenter;

    EditText searchTxt ;

    ArrayList<Meal> meals;

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

        searchMealsAdapter = new SearchMealsAdapter(new ArrayList<>(),getContext(), COUNTRY_MEALS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchMealsAdapter);

        ImageView back = view.findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        searchTxt = view.findViewById(R.id.searchTXT);
        searchTxt.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                             }

                                             // another method
                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                 Observable observable = Observable.create(
                                                         item -> {
                                                             String e = s.toString();
                                                             item.onNext(e);
                                                         }
                                                 ).debounce(300, TimeUnit.MILLISECONDS);

                                                 observable.observeOn(AndroidSchedulers.mainThread())
                                                         .subscribe(str -> {
                                                             if (meals != null) {
                                                                 ArrayList<Meal> result = (ArrayList<Meal>) meals.stream().filter(item -> item.getStrMeal().toLowerCase().contains(str.toString().toLowerCase())).collect(Collectors.toList());
                                                                 searchMealsAdapter.setList(result);
                                                             }

                                                         });

                                             }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String strCountry = CountriesMealFragmentArgs.fromBundle(getArguments()).getStrCountry();

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);
        presenter.getMealsByCountry(strCountry);
    }

    @Override
    public void showMealsData(Meals meals) {
        this.meals = meals.getMeals();
        searchMealsAdapter.setList(meals.getMeals());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}