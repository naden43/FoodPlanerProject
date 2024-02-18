package com.example.food_planer.network;

import com.example.food_planer.model.Categories;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.Meals;
import com.example.food_planer.model.MealsDetails;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class FoodRemoteSourceImpl implements FoodRemoteDataSource {


    private static FoodRemoteSourceImpl instance = null;

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    Retrofit retrofit;

    private FoodRemoteSourceImpl() {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build();
    }

    public static FoodRemoteSourceImpl getInstance() {
        if (instance == null) {
            instance = new FoodRemoteSourceImpl();
        }
        return instance;
    }

    public void makeACategoiresCall(CategoriesNetworkCallBack categoriesNetworkCallBack) {
        CategoryService categoryService = retrofit.create(CategoryService.class);

        Single<Categories> call = categoryService.getAllCategories();

        call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(

                                        data ->categoriesNetworkCallBack.onSuccess(data),
                                        error -> categoriesNetworkCallBack.onFailure(error.getMessage())

                                );



    }

    public void makeACountriesCall(CountriesNetworkCallBack countriesNetworkCallBack) {

        CountryService countryService = retrofit.create(CountryService.class);

        Single<Countries> call = countryService.getAllCountries();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        data ->countriesNetworkCallBack.onSuccess(data),
                        error ->countriesNetworkCallBack.onFailure(error.getMessage())


                );
    }

    public void makeAIngredientsCall(IngredientNetworkCallBack ingredientNetworkCallBack) {

        IngredientServices ingredientServices = retrofit.create(IngredientServices.class);

        Single<Ingredients> call = ingredientServices.getAllIngredents();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        data ->ingredientNetworkCallBack.onSuccess(data),

                        error ->ingredientNetworkCallBack.onFailure(error.getMessage())

                );


    }

    public void makeAInspirationMealCall(InspirationMealNetworkCallBack inspirationMealNetworkCallBack) {
        RandomMealService randomMealService = retrofit.create(RandomMealService.class);

        Single<Meals> call = randomMealService.getRandomMeal();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        data ->inspirationMealNetworkCallBack.onSuccess(data),

                        error -> inspirationMealNetworkCallBack.onFailure(error.getMessage())

                );
    }

    @Override
    public void makeARandomMealCall(MealCallBack mealCallBack) {
        RandomMealService randomMealService = retrofit.create(RandomMealService.class);

        Single<Meals> call = randomMealService.getRandomMeal();

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data ->mealCallBack.onSuccess(data.getMeals().get(0)),
                        error -> mealCallBack.onFailure(error.getMessage())
                );
    }

    @Override
    public void makeAMealByCountryCall(SearchMealsCallBack searchMealsCallBack, String CountryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Single<Meals> call = searchByService.getMealsByCountry(CountryName);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data ->searchMealsCallBack.onSuccess(data),
                        error -> searchMealsCallBack.onFailure(error.getMessage())
                );

    }

    @Override
    public void makeAMealsByCategoryCall(SearchMealsCallBack searchMealsCallBack, String CategoryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Single<Meals> call = searchByService.getMealsByCategory(CategoryName);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data ->searchMealsCallBack.onSuccess(data),
                        error -> searchMealsCallBack.onFailure(error.getMessage())
                );

    }

    @Override
    public void makeAMealsByIngredientCall(SearchMealsCallBack searchMealsCallBack, String IngredientName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Single<Meals> call = searchByService.getMealsByIngredient(IngredientName);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data ->searchMealsCallBack.onSuccess(data),
                        error -> searchMealsCallBack.onFailure(error.getMessage())
                );

    }

    public void makeAMealDetailsCall(MealDetailsCallBack mealDetailsCallBack, String strMeal) {
        MealService mealService = retrofit.create(MealService.class);

        Single<MealsDetails> call = mealService.getFullDetailedMeal(strMeal);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        data -> mealDetailsCallBack.onSuccess(data.getMeals().get(0)),
                        error -> mealDetailsCallBack.onFailure(error.getMessage())

                );


    }
}