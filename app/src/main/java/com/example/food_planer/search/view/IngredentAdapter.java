package com.example.food_planer.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_planer.R;
import com.example.food_planer.model.Country;
import com.example.food_planer.model.Ingredien;

import java.util.ArrayList;

public class IngredentAdapter extends RecyclerView.Adapter<IngredentAdapter.ViewHolder>{
    ArrayList<Ingredien> Ingredents;
    Context context ;

    public IngredentAdapter(ArrayList<Ingredien> countries , Context context) {
        this.Ingredents = countries;
        this.context = context;
    }

    public void setList(ArrayList<Ingredien> countries)
    {
        this.Ingredents = countries;
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
    public IngredentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_item_search , parent , false);
        IngredentAdapter.ViewHolder viewHolder = new IngredentAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredentAdapter.ViewHolder holder, int position) {
        holder.name.setText(Ingredents.get(position).getStrIngredient());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDirections.ActionSearchToIngredentsMealsFragment action = SearchDirections.actionSearchToIngredentsMealsFragment();
                action.setStrIgredent(Ingredents.get(position).getStrIngredient());
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Ingredents.size();
    }
}
