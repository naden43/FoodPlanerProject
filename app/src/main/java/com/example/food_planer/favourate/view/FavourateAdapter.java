package com.example.food_planer.favourate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.food_planer.R;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.search.view.SearchMealsAdapter;
import com.example.food_planer.searchbycategory.view.CategoryMealsFragmentDirections;
import com.example.food_planer.searchbycountry.view.CountriesMealFragmentDirections;
import com.example.food_planer.searchbyingredents.view.ingredentsMealsFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FavourateAdapter extends  RecyclerView.Adapter<FavourateAdapter.ViewHolder>{


    ArrayList<MealDetail> meals ;
    Context context ;

    deleteSetOnClickListener listener;
    int id ;

    public FavourateAdapter(ArrayList<MealDetail> meals ,deleteSetOnClickListener listener ,Context context) {
        this.meals = meals;
        this.context = context;
        this.listener = listener;
        this.id =id ;
    }

    public void setList(ArrayList<MealDetail> meals)
    {
        this.meals = meals;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView description ;

        public ImageView img ;

        public ConstraintLayout constraintLayout;

        FloatingActionButton delete;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            constraintLayout = itemView.findViewById(R.id.constarinView);
            description = itemView.findViewById(R.id.mealName);
            img = itemView.findViewById(R.id.img);
            delete = itemView.findViewById(R.id.delete);
        }
    }
    @NonNull
    @Override
    public FavourateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.favourate_item , parent , false);
        FavourateAdapter.ViewHolder viewHolder = new FavourateAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavourateAdapter.ViewHolder holder, int position) {

        holder.description.setText(meals.get(position).getStrMeal());

        holder.img.setImageBitmap(meals.get(position).image);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteFavourateItem(meals.get(position));
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavourateDirections.ActionFavourateToMealDetails action = FavourateDirections.actionFavourateToMealDetails();
                action.setStrMeal(meals.get(position).strMeal);
                action.setId(1);
                Navigation.findNavController(v).navigate(action);
            }
        });
        /*holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id == 1){
                    CategoryMealsFragmentDirections.ActionCategoryMealsFragmentToMealDetails action = CategoryMealsFragmentDirections.actionCategoryMealsFragmentToMealDetails();
                    action.setStrMeal(meals.get(position).getStrMeal());
                    Navigation.findNavController(v).navigate(action);

                else if(id==2){
                    CountriesMealFragmentDirections.ActionCountriesMealFragmentToMealDetails action = CountriesMealFragmentDirections.actionCountriesMealFragmentToMealDetails();
                    action.setStrMeal(meals.get(position).getStrMeal());
                    Navigation.findNavController(v).navigate(action);
                }
                else{
                    ingredentsMealsFragmentDirections.ActionIngredentsMealsFragmentToMealDetails action = ingredentsMealsFragmentDirections.actionIngredentsMealsFragmentToMealDetails();
                    action.setStrMeal(meals.get(position).getStrMeal());
                    Navigation.findNavController(v).navigate(action);
                }


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
