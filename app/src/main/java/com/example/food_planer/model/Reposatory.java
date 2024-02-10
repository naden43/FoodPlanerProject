package com.example.food_planer.model;

import android.content.SharedPreferences;

import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.NetworkCallback;

public class Reposatory {

   private static Reposatory instance = null;

   UserLocalDataSourceimpl rememberFile ;
   FireBaseAuth auth ;

   private Reposatory(UserLocalDataSourceimpl rememberFile , FireBaseAuth auth){
       this.rememberFile = rememberFile;
       this.auth = auth;
   }

   public static  Reposatory getInstance(UserLocalDataSourceimpl rememberFile , FireBaseAuth auth){
       if(instance==null)
       {
           instance = new Reposatory(rememberFile , auth);
       }
       return instance;
   }

   public void addUser(String email , String password){
       rememberFile.addCredintialsTOfile(email , password);
   }

   public void makeAddCall(String email , String password , boolean remember,NetworkCallback networkCallback){
       auth.makeAcallAdd(email , password ,remember, networkCallback);
   }
   public boolean checkSavedAccount()
   {
       return rememberFile.checkSavedLogin();
   }
}
