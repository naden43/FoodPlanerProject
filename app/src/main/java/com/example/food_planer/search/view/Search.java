package com.example.food_planer.search.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
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


public class Search extends Fragment implements Isearch {


    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;

    EditText searchText ;
    IngredentAdapter ingredentAdapter;

    Presenter presenter;

    ArrayList<Category> categories = new ArrayList<>();

    ArrayList<Country> countries = new ArrayList<>();

    ArrayList<Ingredien> ingrediens = new ArrayList<>();

    ConstraintLayout page ;
    ConstraintLayout netWorkMessage;

    ConstraintLayout loading ;
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

        page = view.findViewById(R.id.pageScroll);
        netWorkMessage = view.findViewById(R.id.networkMessage);
        loading = view.findViewById(R.id.loading);

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

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);
        presenter.getAllCategories();







    }

    @Override
    public void showCategoriesData(Categories categories) {
        loading.setVisibility(View.GONE);
        page.setVisibility(View.VISIBLE);
        this.categories = categories.getCategories();
        categoryAdapter.setList(categories.getCategories());
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCountriesData(Countries countries) {
        loading.setVisibility(View.GONE);
        page.setVisibility(View.VISIBLE);
        this.countries = countries.getCountries();
        countryAdapter.setList(countries.getCountries());
    }

    @Override
    public void showIngredentsData(Ingredients ingredients){
        loading.setVisibility(View.GONE);
        page.setVisibility(View.VISIBLE);
        this.ingrediens = ingredients.getIngredents();
        ingredentAdapter.setList(ingredients.getIngredents());
    }

    @Override
    public void onStart() {
        super.onStart();
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
                        Log.i("TAG", "run: " + "here");
                        netWorkMessage.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);
                        presenter.getAllCategories();
                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", "run: " + "here lost");
                        page.setVisibility(View.GONE);
                        netWorkMessage.setVisibility(View.VISIBLE);
                    }
                });
            }
        };


        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    }
}