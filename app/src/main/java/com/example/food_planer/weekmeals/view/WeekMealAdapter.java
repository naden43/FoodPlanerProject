package com.example.food_planer.weekmeals.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planer.R;
import com.example.food_planer.home.view.HomeDirections;
import com.example.food_planer.mealpage.view.IngredientAdapter;
import com.example.food_planer.model.Meal;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.model.WeekMealsDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeekMealAdapter extends RecyclerView.Adapter<WeekMealAdapter.ViewHolder> {

    ArrayList<WeekMealDetail> meals ;
    Context context ;

    public WeekMealAdapter(ArrayList<WeekMealDetail> meals , Context context) {
        this.meals = meals;
        this.context = context;
    }

    public void setList(ArrayList<WeekMealDetail> meal)
    {
        this.meals = meal;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView instrText ;
        public TextView description ;

        public ImageView img ;

        public ConstraintLayout constraintLayout;

        public View layout;

        RecyclerView ingredients;

        WebView videoView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            constraintLayout = itemView.findViewById(R.id.constarinView);
            description = itemView.findViewById(R.id.mealName);
            img = itemView.findViewById(R.id.foodImage);
            ingredients = itemView.findViewById(R.id.ingredientsRecycularView);
            instrText = itemView.findViewById(R.id.instructionsText);
            videoView = itemView.findViewById(R.id.video);
        }
    }
    @NonNull
    @Override
    public WeekMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.activity_week_meal_item , parent , false);
        WeekMealAdapter.ViewHolder viewHolder = new WeekMealAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeekMealAdapter.ViewHolder holder, int position) {

        holder.description.setText(meals.get(position).getStrMeal());
        Glide.with(context)
                .load(meals.get(position).mealDetail.getStrMealThumb())
                .apply(new RequestOptions().override(200, 200))
                .error(R.drawable.backtwo)
                .into( holder.img);


        Log.i("TAG", "onBindViewHolder: " + meals.get(position).mealDetail.image.getRowBytes());
        IngredientAdapter myAdapter = new IngredientAdapter(meals.get(position).getMealDetail().ingredientsMeasure(meals.get(position).getMealDetail()), context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.ingredients.setLayoutManager(layoutManager);
        holder.ingredients.setAdapter(myAdapter);

        String[] instructions = meals.get(position).getMealDetail().getStrInstructions().split("[.]");
        holder.instrText.setText(instructions[0]);
        Log.i("TAG", "showMealData: " + instructions[0]);
        for (int i = 1; i < instructions.length; i++) {
            holder.instrText.setText(holder.instrText.getText() + "\n" + instructions[i]);
            holder.instrText.setText(holder.instrText.getText() + "\n");
        }




        String videoId = extractVideoId(meals.get(position).getMealDetail().getStrYoutube());
        String embeddedVideoUrl = "https://www.youtube.com/embed/" + videoId;
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + embeddedVideoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        holder.videoView.loadData(video, "text/html", "utf-8");
        holder.videoView.getSettings().setJavaScriptEnabled(true);
        holder.videoView.setWebChromeClient(new WebChromeClient());

    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }
}
