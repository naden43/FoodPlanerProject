package com.example.food_planer.weekmeals.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planer.R;
import com.example.food_planer.model.Category;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.search.view.CategoryAdapter;
import com.example.food_planer.search.view.SearchDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder>{
    ArrayList<WeekMealDetail> meals;
    Context context ;

    OnDeleteClickListener listener;
    public MealAdapter(ArrayList<WeekMealDetail> meals , Context context , OnDeleteClickListener listener) {
        this.meals = meals;
        this.context = context;
        this.listener = listener;
    }

    public void setList(ArrayList<WeekMealDetail>categories)
    {
        this.meals = categories;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name ;

        public ConstraintLayout constraintLayout;

        ImageView img ;

        FloatingActionButton btnDelete ;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            name = itemView.findViewById(R.id.foodName);
            constraintLayout = itemView.findViewById(R.id.constarinView);
            img = itemView.findViewById(R.id.foodImage);
            btnDelete = itemView.findViewById(R.id.delete);
        }
    }
    @NonNull
    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_plan_item , parent , false);
        MealAdapter.ViewHolder viewHolder = new MealAdapter.ViewHolder(itemView);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
        holder.name.setText(meals.get(position).getStrMeal());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SearchDirections.ActionSearchToCategoryMealsFragment action = SearchDirections.actionSearchToCategoryMealsFragment(meals.get(position).getStrCategory());
                //Navigation.findNavController(v).navigate(action);

                WeekMealsDirections.ActionWeekMealsToMealDetails action = WeekMealsDirections.actionWeekMealsToMealDetails();
                action.setDay(meals.get(position).Day);
                action.setYear(meals.get(position).year);
                action.setMonth(meals.get(position).month);
                action.setStrMeal(meals.get(position).getMealDetail().strMeal);
                action.setId(2);
                Navigation.findNavController(v).navigate(action);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(meals.get(position));
            }
        });

        Glide.with(context)
                .load(meals.get(position).mealDetail.getStrMealThumb())
                .apply(new RequestOptions().override(200, 200))
                .error(R.drawable.backtwo)
                .into( holder.img);

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
