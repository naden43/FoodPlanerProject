package com.example.food_planer.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planer.R;
import com.example.food_planer.model.Meal;

import java.util.ArrayList;

public class SearchMealsAdapter extends  RecyclerView.Adapter<SearchMealsAdapter.ViewHolder>{

    ArrayList<Meal> meals ;
    Context context ;

    public SearchMealsAdapter(ArrayList<Meal> meals , Context context) {
        this.meals = meals;
        this.context = context;
    }

    public void setList(ArrayList<Meal> meals)
    {
         this.meals = meals;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView description ;

        public ImageView img ;

        public ConstraintLayout constraintLayout;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            constraintLayout = itemView.findViewById(R.id.constarinView);
            description = itemView.findViewById(R.id.mealName);
            img = itemView.findViewById(R.id.img);
        }
    }
    @NonNull
    @Override
    public SearchMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_item_meal_search , parent , false);
        SearchMealsAdapter.ViewHolder viewHolder = new SearchMealsAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMealsAdapter.ViewHolder holder, int position) {

        holder.description.setText(meals.get(position).getStrMeal());
        // separate ?
        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().
                override(200, 200)).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
