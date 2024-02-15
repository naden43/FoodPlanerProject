package com.example.food_planer.model;

import com.example.food_planer.network.FireBaseAuth;
import com.example.food_planer.network.NetworkCallback;

public class LoginAndRegisterReposatory {

   private static LoginAndRegisterReposatory instance = null;

   UserLocalDataSourceimpl rememberFile ;
   FireBaseAuth auth ;

   private LoginAndRegisterReposatory(UserLocalDataSourceimpl rememberFile , FireBaseAuth auth){
       this.rememberFile = rememberFile;
       this.auth = auth;
   }

   public static LoginAndRegisterReposatory getInstance(UserLocalDataSourceimpl rememberFile , FireBaseAuth auth){
       if(instance==null)
       {
           instance = new LoginAndRegisterReposatory(rememberFile , auth);
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

   public void userSignIn(String email , String password , Boolean remember , NetworkCallback networkCallback){
       auth.makeSignInCall(email, password, remember, networkCallback );
   }
}
