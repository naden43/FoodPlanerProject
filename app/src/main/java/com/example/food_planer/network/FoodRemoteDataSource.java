package com.example.food_planer.network;

public interface FoodRemoteDataSource {

    public void makeACategoiresCall(CategoriesNetworkCallBack categoriesNetworkCallBack);

    public void makeACountriesCall(CountriesNetworkCallBack countriesNetworkCallBack);

    public void makeAIngredientsCall(IngredientNetworkCallBack ingredientNetworkCallBack);

    public void makeAInspirationMealCall(InspirationMealNetworkCallBack inspirationMealNetworkCallBack);

    public void makeARandomMealCall(RandomMealCallBack randomMealCallBack);

    public void makeAMealByCountryCall(SearchMealsCallBack searchMealsCallBack , String CountryName);
    public void makeAMealsByCategoryCall(SearchMealsCallBack searchMealsCallBack , String CategoryName);

    public void makeAMealsByIngredientCall(SearchMealsCallBack searchMealsCallBack , String IngredientName);

}
