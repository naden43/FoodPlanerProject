package com.example.food_planer.weekmeals.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.search.view.CategoryAdapter;
import com.example.food_planer.weekmeals.presenter.Presenter;


import java.util.ArrayList;
import java.util.Calendar;

public class WeekMeals extends Fragment implements IWeekMeals , OnDeleteClickListener {


    MealAdapter adapter;
    Presenter presenter;
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

        adapter = new MealAdapter(new ArrayList<>(),getContext() , this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2 ,RecyclerView.HORIZONTAL ,false);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(),MealLocalDataSourceimpl.getInstance(getContext()),PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                presenter.getWeekMeal(dayOfMonth ,month ,year);
            }
        });


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

        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()),this);

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
}