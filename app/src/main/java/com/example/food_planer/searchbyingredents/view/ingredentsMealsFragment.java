package com.example.food_planer.searchbyingredents.view;

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
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.Meals;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.SearchMealsAdapter;
import com.example.food_planer.searchbyingredents.presenter.Presenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class ingredentsMealsFragment extends Fragment implements IIngredentsMealFragment {


    public static final int INGREDIENT_MEALS = 3;
    SearchMealsAdapter searchMealsAdapter;

    Presenter presenter;

    EditText searchText;

    ArrayList<Meal> meals;

    ConstraintLayout networkMessage;

    ConstraintLayout page;

    String strIngredient;

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

        networkMessage = view.findViewById(R.id.networkMessage);
        page = view.findViewById(R.id.page);
        searchMealsAdapter = new SearchMealsAdapter(new ArrayList<>(),getContext(),INGREDIENT_MEALS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchMealsAdapter);

       strIngredient = ingredentsMealsFragmentArgs.fromBundle(getArguments()).getStrIgredent();

        ImageView back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        searchText = view.findViewById(R.id.searchTxt);
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
                            if(meals!=null){
                                ArrayList<Meal> result = (ArrayList<Meal>) meals.stream().filter(item -> item.getStrMeal().toLowerCase().contains(str.toString().toLowerCase())).collect(Collectors.toList());
                                searchMealsAdapter.setList(result);
                            }
                        });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);
        presenter.getMealsByIngredient(strIngredient);
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
                        networkMessage.setVisibility(View.GONE);
                        page.setVisibility(View.VISIBLE);
                        presenter.getMealsByIngredient(strIngredient);
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
                        networkMessage.setVisibility(View.VISIBLE);

                    }
                });
            }
        };


        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    }
}