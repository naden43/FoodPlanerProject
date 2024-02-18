package com.example.food_planer.dpfirestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.food_planer.model.DataBaseDelegate;
import com.example.food_planer.model.MealDetail;
import com.example.food_planer.model.WeekMealDetail;
import com.example.food_planer.network.FireBaseResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FireStoreRemoteDataSourceimpl implements FireStoreRemoteDataSource {

    private static final String FAVOURITE = "favourates";
    private static final String PLANS = "plans";
    private static FireStoreRemoteDataSourceimpl instance = null;

    FirebaseFirestore db;

    private FireStoreRemoteDataSourceimpl()
    {
        db = FirebaseFirestore.getInstance();
    }

    public static FireStoreRemoteDataSourceimpl getInstance()
    {
        if(instance==null)
        {
            instance = new FireStoreRemoteDataSourceimpl();
        }
        return instance;
    }

    @Override
    public void saveFavouriteMeals(ArrayList<MealDetail> favoriteMeals, String userId)
    {

        Map<String, Object> data = new HashMap<>();
        Gson gson = new Gson();
        String gFavourate = gson.toJson(favoriteMeals);
        data.put(FAVOURITE , gFavourate);
        db.collection("Users").document(userId).set(data, SetOptions.merge());
    }

    @Override
    public void savePlanMeals(ArrayList<WeekMealDetail> planMeals, String userId){
        Map<String, Object> data = new HashMap<>();
        Gson gson = new Gson();
        String gPlans = gson.toJson(planMeals);
        data.put(PLANS , gPlans);
        db.collection("Users").document(userId)
                .set(data, SetOptions.merge());

    }

    public void getSavedMeals(String userId , FireBaseResponse fireBaseResponse)
    {

        Log.i("TAG", "getSavedMeals: " + userId);
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Gson gson = new Gson();

                        String gFavourite = (String) document.getData().get(FAVOURITE);

                        Log.i("TAG", "onComplete: " + gFavourite);
                        Type favouriteListType = new TypeToken<ArrayList<MealDetail>>(){}.getType();
                        ArrayList<MealDetail> mealDetailList = gson.fromJson(gFavourite, favouriteListType);

                        String gPlans = (String) document.getData().get(PLANS);

                        Type plansListType = new TypeToken<ArrayList<WeekMealDetail>>(){}.getType();
                        ArrayList<WeekMealDetail> planMeals = gson.fromJson(gPlans, plansListType);

                        fireBaseResponse.sendData(planMeals , mealDetailList);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

}
