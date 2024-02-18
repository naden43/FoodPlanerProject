package com.example.food_planer.weekmeals.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;
import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.register.view.RegisterActivity;
import com.example.food_planer.search.view.CategoryAdapter;
import com.example.food_planer.weekmeals.presenter.Presenter;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeekMeals extends Fragment implements IWeekMeals , OnDeleteClickListener {


    MealAdapter adapter;
    Presenter presenter;

    Button signUp;

    ConstraintLayout guestMessage;

    ConstraintLayout page ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.savedMeals);
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        page = view.findViewById(R.id.page);
        guestMessage = view.findViewById(R.id.guestMessage);
        signUp = view.findViewById(R.id.siginUpRedirect);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , RegisterActivity.class));
                getActivity().finish();
            }
        });
        adapter = new MealAdapter(new ArrayList<>(),getContext() , this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2 ,RecyclerView.HORIZONTAL ,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(),MealLocalDataSourceimpl.getInstance(getContext()),PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this , LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(getContext()),FireBaseAuth.getInstance(null)));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                presenter.getWeekMeal(dayOfMonth ,month ,year);
            }
        });

        presenter.getUserMode();


    }

    @Override
    public void showMeals(ArrayList<WeekMealDetail> weekMealDetails) {
        adapter.setList(weekMealDetails);
    }

    public void showDataPicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        presenter.stopSubscribe();
                        presenter.getWeekMeal(dayOfMonth , month  , year);

                    }
                },year , mounth , day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-100);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void delete(WeekMealDetail weekMealDetail) {
        presenter.deletePlanMeal(weekMealDetail);
    }
    @Override
    public void showLoginOrRegisterMsg() {
        page.setVisibility(View.GONE);
        guestMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserContent() {

        page.setVisibility(View.VISIBLE);
        guestMessage.setVisibility(View.GONE);

    }

}