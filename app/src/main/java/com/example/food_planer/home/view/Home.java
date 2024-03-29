package com.example.food_planer.home.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;


import com.example.food_planer.NavigationActivity;
import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.home.presenter.Presenter;
import com.example.food_planer.login.view.LoginActivity;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Home extends Fragment implements Ihome {

    private static final String TAG = "Home";

    Adapter myAdapter;
    Adapter myAdapterRandom;

    LinearLayoutManager layoutManagerRandom;

    Presenter presenter;

    ConstraintLayout networkLayout;

    ScrollView page;

    ConstraintLayout loading ;

    boolean status ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networkLayout = view.findViewById(R.id.networkMessage);
        page = view.findViewById(R.id.homePage);
        loading = view.findViewById(R.id.loading);
        ImageView logoutBtn  = view.findViewById(R.id.signout);




        RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.recycularView);
        RecyclerView recyclerView1 = view.findViewById(R.id.randomRecycularView);


        myAdapter = new Adapter(new ArrayList<>(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        myAdapterRandom = new Adapter(new ArrayList<>(), getContext());
        layoutManagerRandom = new LinearLayoutManager(getContext());
        layoutManagerRandom.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManagerRandom);
        recyclerView1.setAdapter(myAdapterRandom);

        Reposatory repo = Reposatory.getInstance(FoodRemoteSourceImpl.getInstance() , MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance());
        presenter = new Presenter(repo, Home.this , LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(getContext()), FireBaseAuth.getInstance(getActivity())));

        Log.i(TAG, "onViewCreated: " + "before");

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!status){
                    Dialog dialog  = new Dialog(getContext());
                    dialog.setContentView(R.layout.login_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.custom_dialog));
                    Button sigin = dialog.findViewById(R.id.sigin);
                    sigin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    });
                    dialog.show();
                }
                else {
                    String userId = presenter.getUserId();
                    presenter.saveFavouriteMeals(userId);
                    presenter.savePlansMeals(userId);
                    presenter.deleteUser();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    try {
                        getActivity().finish();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        status = presenter.getUserMode();

    }

    @Override
    public void showData(Meal meal) {
        loading.setVisibility(View.GONE);
        page.setVisibility(View.VISIBLE);
        myAdapter.setToList(meal);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), "fail" + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomData(Meal meal) {
        myAdapterRandom.setToList(meal);
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
                        Log.i(TAG, "run: " + "here");
                        networkLayout.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);
                        presenter.getInspirationMeals();
                        presenter.getRandomMeals();
                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + "here lost");
                        page.setVisibility(View.GONE);
                        networkLayout.setVisibility(View.VISIBLE);
                        myAdapter.clearList();
                        myAdapterRandom.clearList();
                    }
                });
            }
        };


        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    }
}