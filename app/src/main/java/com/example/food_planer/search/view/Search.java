package com.example.food_planer.search.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.model.Category;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Country;
import com.example.food_planer.model.Ingredien;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.presenter.Presenter;
import com.example.food_planer.model.Categories;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;


public class Search extends Fragment implements Isearch {


    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;

    EditText searchText ;
    IngredentAdapter ingredentAdapter;

    Presenter presenter;

    ArrayList<Category> categories = new ArrayList<>();

    ArrayList<Country> countries = new ArrayList<>();

    ArrayList<Ingredien> ingrediens = new ArrayList<>();
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

        RecyclerView searchList = view.findViewById(R.id.SearchList);

        TabLayout searchBy = view.findViewById(R.id.tabLayout);

       searchBy.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.i("TAG", "onTabSelected: "+ tab.getText());
                if(tab.getText().equals("Category")){

                    categoryAdapter = new CategoryAdapter(new ArrayList<>(),getContext());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    searchList.setLayoutManager(layoutManager);
                    searchList.setAdapter(categoryAdapter);
                    presenter.getAllCategories();
                }
                else if(tab.getText().equals("Country")){
                    countryAdapter = new CountryAdapter(new ArrayList<>(),getContext());
                    LinearLayoutManager countrylayoutManager = new LinearLayoutManager(getContext());
                    countrylayoutManager.setOrientation(RecyclerView.VERTICAL);
                    searchList.setLayoutManager(countrylayoutManager);
                    searchList.setAdapter(countryAdapter);
                    presenter.getAllCountries();
                }
                else {
                    ingredentAdapter = new IngredentAdapter(new ArrayList<>(),getContext());
                    LinearLayoutManager ingredentslayoutManager = new LinearLayoutManager(getContext());
                    ingredentslayoutManager.setOrientation(RecyclerView.VERTICAL);
                    searchList.setLayoutManager(ingredentslayoutManager);
                    searchList.setAdapter(ingredentAdapter);
                    presenter.getAllIngredents();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        categoryAdapter = new CategoryAdapter(new ArrayList<>(),getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        searchList.setLayoutManager(layoutManager);
        searchList.setAdapter(categoryAdapter);

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext())),this);
        presenter.getAllCategories();



        /*ConstraintLayout networkLayout = view.findViewById(R.id.networkMessage);
        ScrollView page = view.findViewById(R.id.searchPage);


        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        Handler mHandler = new Handler(Looper.getMainLooper());
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){

            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        networkLayout.setVisibility(View.GONE);
                        page.setVisibility(View.VISIBLE);
                        presenter.getAllCategories();
                        presenter.getAllCountries();
                        presenter.getAllIngredents();

                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        page.setVisibility(View.GONE);
                        networkLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        };


        searchText = view.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // another method
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Observable observable = Observable.create(
                        item-> {
                            String e = s.toString();
                            item.onNext(e);
                        }
                ).debounce(300 , TimeUnit.MILLISECONDS);

                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribe( str -> {
                    if(categories!=null) {
                        ArrayList<Category> result = (ArrayList<Category>) categories.stream().filter(item -> item.getStrCategory().toLowerCase().contains(str.toString().toLowerCase())).collect(Collectors.toList());
                        categoryAdapter.setList(result);
                    }
                    if(countries!=null) {
                                ArrayList<Country> result = (ArrayList<Country>) countries.stream().filter(item -> item.getStrArea().toLowerCase().contains(str.toString().toLowerCase())).collect(Collectors.toList());
                                countryAdapter.setList(result);
                    }
                    if(ingrediens!=null){
                        ArrayList<Ingredien> result = (ArrayList<Ingredien>) ingrediens.stream().filter(item -> item.getStrIngredient().toLowerCase().contains(str.toString().toLowerCase())).collect(Collectors.toList());
                        ingredentAdapter.setList(result);
                    }
                });

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);


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



        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext())),this);
*/





    }

    @Override
    public void showCategoriesData(Categories categories) {
        this.categories = categories.getCategories();
        categoryAdapter.setList(categories.getCategories());
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCountriesData(Countries countries) {
        this.countries = countries.getCountries();
        countryAdapter.setList(countries.getCountries());
    }

    @Override
    public void showIngredentsData(Ingredients ingredients){
        this.ingrediens = ingredients.getIngredents();
        ingredentAdapter.setList(ingredients.getIngredents());
    }
}