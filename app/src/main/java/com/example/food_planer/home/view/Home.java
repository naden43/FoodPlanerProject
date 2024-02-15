package com.example.food_planer.home.view;

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
import android.widget.ScrollView;
import android.widget.Toast;


import com.example.food_planer.R;
import com.example.food_planer.home.presenter.Presenter;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.FoodRemoteSourceImpl;

import java.util.ArrayList;



public class Home extends Fragment implements Ihome {

    private static final String TAG = "Home";

    Adapter myAdapter;
    Adapter myAdapterRandom;

    LinearLayoutManager layoutManagerRandom;

    Presenter presenter;
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

        ConstraintLayout networkLayout = view.findViewById(R.id.networkMessage);
        ScrollView page = view.findViewById(R.id.homePage);

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
                        page.setVisibility(View.VISIBLE);
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
                    }
                });
            }
        };


        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.requestNetwork(networkRequest , networkCallback);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

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

        Reposatory repo = Reposatory.getInstance(FoodRemoteSourceImpl.getInstance());
        presenter = new Presenter(repo, Home.this);

        Log.i(TAG, "onViewCreated: " + "before");
        /*if(activeNetwork!=null && activeNetwork.isConnectedOrConnecting()) {

            Log.i(TAG, "onViewCreated: " +"visiable");
            page.setVisibility(View.VISIBLE);
            networkLayout.setVisibility(View.GONE);

        }
        else {
            Log.i(TAG, "onViewCreated: " + "here");
            page.setVisibility(View.GONE);
            networkLayout.setVisibility(View.VISIBLE);

        }*/

    }

    @Override
    public void showData(Meal meal) {
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


}