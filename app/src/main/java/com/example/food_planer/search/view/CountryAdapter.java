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

import com.example.food_planer.R;
import com.example.food_planer.model.Category;
import com.example.food_planer.model.Country;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    ArrayList<Country> countries;
    Context context ;

    public CountryAdapter(ArrayList<Country> countries , Context context) {
        this.countries = countries;
        this.context = context;
    }

    public void setList(ArrayList<Country> countries)
    {
        this.countries = countries;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name ;

        public ConstraintLayout constraintLayout;

        public View layout;

        ImageView image ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            name = itemView.findViewById(R.id.name);
            constraintLayout = itemView.findViewById(R.id.constarinView);
            image = itemView.findViewById(R.id.image);
        }
    }
    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_item_search , parent , false);
        CountryAdapter.ViewHolder viewHolder = new CountryAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        holder.name.setText(countries.get(position).getStrArea());


        String strArea = countries.get(position).getStrArea();
        int resId = holder.itemView.getContext().getResources().getIdentifier(strArea.toLowerCase(), "drawable", holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(resId);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDirections.ActionSearchToCountriesMealFragment action = SearchDirections.actionSearchToCountriesMealFragment();
                action.setStrCountry(countries.get(position).getStrArea());
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
