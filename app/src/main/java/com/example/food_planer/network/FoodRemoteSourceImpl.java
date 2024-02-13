package com.example.food_planer.network;

import com.example.food_planer.model.Categories;
import com.example.food_planer.model.Countries;
import com.example.food_planer.model.Ingredients;
import com.example.food_planer.model.meals;

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

        Call<meals> call  = randomMealService.getRandomMeal();

        call.enqueue(new Callback<meals>() {
            @Override
            public void onResponse(Call<meals> call, Response<meals> response) {
                inspirationMealNetworkCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<meals> call, Throwable t) {
                inspirationMealNetworkCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeARandomMealCall(RandomMealCallBack randomMealCallBack) {
        RandomMealService randomMealService = retrofit.create(RandomMealService.class);

        Call<meals> call  = randomMealService.getRandomMeal();

        call.enqueue(new Callback<meals>() {
            @Override
            public void onResponse(Call<meals> call, Response<meals> response) {
                randomMealCallBack.onSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<meals> call, Throwable t) {
                randomMealCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealByCountryCall(SearchMealsCallBack searchMealsCallBack, String CountryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<meals> call  = searchByService.getMealsByCountry(CountryName);

        call.enqueue(new Callback<meals>() {
            @Override
            public void onResponse(Call<meals> call, Response<meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealsByCategoryCall(SearchMealsCallBack searchMealsCallBack , String CategoryName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<meals> call  = searchByService.getMealsByCategory(CategoryName);

        call.enqueue(new Callback<meals>() {
            @Override
            public void onResponse(Call<meals> call, Response<meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void makeAMealsByIngredientCall(SearchMealsCallBack searchMealsCallBack, String IngredientName) {
        SearchByService searchByService = retrofit.create(SearchByService.class);

        Call<meals> call  = searchByService.getMealsByIngredient(IngredientName);

        call.enqueue(new Callback<meals>() {
            @Override
            public void onResponse(Call<meals> call, Response<meals> response) {
                searchMealsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<meals> call, Throwable t) {
                searchMealsCallBack.onFailure(t.getMessage());
            }
        });
    }


}
