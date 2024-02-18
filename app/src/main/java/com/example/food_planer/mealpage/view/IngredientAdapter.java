package com.example.food_planer.mealpage.view;

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

import com.example.food_planer.model.IngredientMesure;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{

    ArrayList<IngredientMesure> ingrediens ;
    Context context ;

    public IngredientAdapter(ArrayList<IngredientMesure> ingrediens , Context context) {
        this.ingrediens = ingrediens;
        this.context = context;
    }

    public void setList(ArrayList<IngredientMesure> ingrediens)
    {
        this.ingrediens = ingrediens;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name ;
        public TextView description ;

        public ImageView img ;

        public ConstraintLayout constraintLayout;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            description = itemView.findViewById(R.id.mesure);
            img = itemView.findViewById(R.id.ingredientImg);
        }
    }
    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_ingredient_item , parent , false);
        IngredientAdapter.ViewHolder viewHolder = new IngredientAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {

        holder.description.setText(ingrediens.get(position).getMeasure());
        // separate ?

        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingrediens.get(position).getIngredient()+"-Small.png").apply(new RequestOptions().
                override(200, 200)).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return ingrediens.size();
    }
}
