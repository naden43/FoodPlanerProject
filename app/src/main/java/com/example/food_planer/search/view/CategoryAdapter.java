package com.example.food_planer.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.food_planer.model.Meal;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categories;
    Context context ;

    public CategoryAdapter(ArrayList<Category> meals , Context context) {
        this.categories = meals;
        this.context = context;
    }

    public void setList(ArrayList<Category>categories)
    {
        this.categories = categories;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name ;

        public ConstraintLayout constraintLayout;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            name = itemView.findViewById(R.id.name);
            constraintLayout = itemView.findViewById(R.id.constarinView);
        }
    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_item_search , parent , false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(itemView);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getStrCategory());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDirections.ActionSearchToCategoryMealsFragment action = SearchDirections.actionSearchToCategoryMealsFragment(categories.get(position).getStrCategory());
                Navigation.findNavController(v).navigate(action);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
