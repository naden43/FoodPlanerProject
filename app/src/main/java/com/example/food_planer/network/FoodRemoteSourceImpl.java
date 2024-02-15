package com.example.food_planer.network;

import com.example.food_planer.model.Categories;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.Meals;
import com.example.food_planer.model.MealsDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodRemoteSourceImpl implements FoodRemoteDataSource{


    private static FoodRemoteSourceImpl instance = null;

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    Retrofit retrofit;
    private FoodRemoteSourceImpl()
    {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
    }

    public static FoodRemoteSourceImpl getInstance()
    {
        if(instance==null)
        {
            instance = new FoodRemoteSourceImpl();
        }
        return instance;
    }
    public void makeACategoiresCall(CategoriesNetworkCallBack categoriesNetworkCallBack) {
        CategoryService categoryService = retrofit.create(CategoryService.class);

        Call<Categories> call  = categoryService.getAllCategories();

        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //Log.i(TAG, "onResponse: " + response.body());
                categoriesNetworkCallBack.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                categoriesNetworkCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void makeACountriesCall(CountriesNetworkCallBack countriesNetworkCallBack) {

       CountryService countryService = retrofit.create(CountryService.class);

        Call<Countries> call  = countryService.getAllCountries();
        call.enqueue(new Callback<Countries>() {
            @Override
            public void onResponse(Call<Countries> call, Response<Countries> response) {
                //Log.i(TAG, "onResponse: " + response.body());
                countriesNetworkCallBack.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Countries> call, Throwable t) {
                countriesNetworkCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void makeAIngredientsCall(IngredientNetworkCallBack ingredientNetworkCallBack) {

        IngredientServices ingredientServices = retrofit.create(IngredientServices.class);

        Call<Ingredients> call  = ingredientServices.getAllIngredents();
        call.enqueue(new Callback<Ingredients>() {
            @Override
            public void onResponse(Call<Ingredients> call, Response<Ingredients> response) {
                //Log.i(TAG, "onResponse: " + response.body());
                ingredientNetworkCallBack.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<Ingredients> call, Throwable t) {
                ingredientNetworkCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void makeAInspirationMealCall(InspirationMealNetworkCallBack inspirationMealNetworkCallBack) {
        RandomMealService randomMealService = retrofit.create(RandomMealService.class);

        Call<Meals> call  = randomMealService.getRandomMeal();

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                inspirationMealNetworkCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                inspirationMealNetworkCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeARandomMealCall(MealCallBack mealCallBack) {
        RandomMealService randomMealService = retrofit.create(RandomMealService.class);

        Call<Meals> call  = randomMealService.getRandomMeal();

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                mealCallBack.onSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                mealCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealByCountryCall(SearchMealsCallBack searchMealsCallBack, String CountryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<Meals> call  = searchByService.getMealsByCountry(CountryName);

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealsByCategoryCall(SearchMealsCallBack searchMealsCallBack , String CategoryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<Meals> call  = searchByService.getMealsByCategory(CategoryName);

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealsByIngredientCall(SearchMealsCallBack searchMealsCallBack, String IngredientName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<Meals> call  = searchByService.getMealsByIngredient(IngredientName);

        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void makeAMealDetailsCall(MealDetailsCallBack mealDetailsCallBack, String strMeal){
        MealService mealService = retrofit.create(MealService.class);

        Call<MealsDetails> call  = mealService.getFullDetailedMeal(strMeal);

        call.enqueue(new Callback<MealsDetails>() {
            @Override
            public void onResponse(Call<MealsDetails> call, Response<MealsDetails> response) {
                mealDetailsCallBack.onSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<MealsDetails> call, Throwable t) {
                mealDetailsCallBack.onFailure(t.getMessage());
            }
        });
    }


}
