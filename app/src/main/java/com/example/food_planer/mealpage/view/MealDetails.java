package com.example.food_planer.mealpage.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.home.view.HomeDirections;
import com.example.food_planer.login.view.LoginActivity;
import com.example.food_planer.mealpage.presenter.Presenter;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MealDetails extends Fragment implements IMealDetails {


    RecyclerView ingredientList;
    TextView mealName;
    ImageView mealImage;
    TextView instrText;
    WebView videoView;

    boolean firstTime;
    FloatingActionButton savedBtn;

    ImageView back;
    FloatingActionButton favourateBtn;

    Presenter presenter;

    MealDetail meal;

    Boolean status ;

    ScrollView page;
    ConstraintLayout loading ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String strMeal = MealDetailsArgs.fromBundle(getArguments()).getStrMeal();
        int id = MealDetailsArgs.fromBundle(getArguments()).getId();

        page = view.findViewById(R.id.page);
        loading = view.findViewById(R.id.loading);

        mealName = view.findViewById(R.id.mealName);
        ingredientList = view.findViewById(R.id.ingredientsRecycularView);
        mealImage = view.findViewById(R.id.foodImage);
        instrText = view.findViewById(R.id.instructionsText);
        videoView = view.findViewById(R.id.video);
        savedBtn = view.findViewById(R.id.addToPlan);
        favourateBtn = view.findViewById(R.id.favouratebtn);
        back = view.findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        // calender
        savedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!status){
                    showDialog();
                }
                else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    WeekMealDetail weekMealDetail = new WeekMealDetail(meal.strMeal, dayOfMonth, meal, month, year);
                                    presenter.addToPlan(weekMealDetail);

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, dayOfMonth);
                                    long startTime = calendar.getTimeInMillis();
                                    long endTime = startTime + TimeUnit.HOURS.toMillis(1);
                                    Intent intent = new Intent(Intent.ACTION_INSERT);
                                    intent.setData(CalendarContract.Events.CONTENT_URI);
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                                    intent.putExtra(CalendarContract.Events.TITLE, meal.strMeal);

                                    if(intent.resolveActivity(getContext().getPackageManager()) != null) {
                                        startActivity(intent);
                                    }

                                }
                            }

                            , year, mounth, day);
                    //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-100);
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    //datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }

            }
        });

        LoginAndRegisterReposatory reposatory = LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(getContext()), FireBaseAuth.getInstance(null));
        presenter = new Presenter(Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getContext()), PlanMealLocalDataSourceimpl.getInstance(getContext()), FireStoreRemoteDataSourceimpl.getInstance()), this , reposatory);

        if(id==1) {
             presenter.getLocalMealData(strMeal);
             savedBtn.setVisibility(View.GONE);
             favourateBtn.setVisibility(View.GONE);
        }
        else if(id==2)
        {
            int mealDay , mealMonth , mealYear;
            mealMonth = MealDetailsArgs.fromBundle(getArguments()).getMonth();
            mealDay = MealDetailsArgs.fromBundle(getArguments()).getDay();
            mealYear = MealDetailsArgs.fromBundle(getArguments()).getYear();
            presenter.getSpecificPlanMeal(strMeal, mealDay , mealMonth,mealYear);
            savedBtn.setVisibility(View.GONE);
            favourateBtn.setVisibility(View.GONE);
        }
        else {
            presenter.getMealData(strMeal);
        }
        status = presenter.getUserMode();
    }


    @Override
    public void showMealData(MealDetail mealDetails) {
        loading.setVisibility(View.GONE);
        page.setVisibility(View.VISIBLE);
        mealName.setText(mealDetails.getStrMeal());
        meal = mealDetails;
        Glide.with(this)
                .load(mealDetails.getStrMealThumb())
                .apply(new RequestOptions().override(200, 200))
                .into(mealImage);


        IngredientAdapter myAdapter = new IngredientAdapter(mealDetails.ingredientsMeasure(mealDetails), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientList.setLayoutManager(layoutManager);
        ingredientList.setAdapter(myAdapter);

        String[] instructions = mealDetails.getStrInstructions().split("[.]");
        instrText.setText(instructions[0]);
        Log.i("TAG", "showMealData: " + instructions[0]);
        for (int i = 1; i < instructions.length; i++) {
            instrText.setText(instrText.getText() + "\n" + instructions[i]);
            instrText.setText(instrText.getText() + "\n");
        }


        String videoId = extractVideoId(mealDetails.getStrYoutube());
        String embeddedVideoUrl = "https://www.youtube.com/embed/" + videoId;
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + embeddedVideoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        videoView.loadData(video, "text/html", "utf-8");
        videoView.getSettings().setJavaScriptEnabled(true);
        videoView.setWebChromeClient(new WebChromeClient());

        favourateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!status){
                    showDialog();
                }
                else {
                    presenter.addToFavourate(mealDetails);
                }
            }
        });


    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
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

    public void showDialog()
    {
        Dialog dialog  = new Dialog(getContext());
        dialog.setContentView(R.layout.login_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.custom_dialog));

        Button sigin = dialog.findViewById(R.id.sigin);


        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                dialog.dismiss();
                getActivity().finish();
            }
        });

        dialog.show();
    }
}
