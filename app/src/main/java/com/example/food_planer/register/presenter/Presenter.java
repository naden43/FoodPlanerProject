package com.example.food_planer.register.presenter;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food_planer.login.view.ILoginActivity;
import com.example.food_planer.login.view.LoginActivity;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.NetworkCallback;
import com.example.food_planer.register.view.IRegisterActivity;
import com.example.food_planer.register.view.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class Presenter implements IPresenter , NetworkCallback {

    Reposatory repo ;

    IRegisterActivity view ;

    String email ;
    String password;
    public Presenter(Reposatory repo ,  IRegisterActivity view )
    {
        this.repo = repo;
        this.view = view;
    }


    @Override
    public void successCall(boolean remember) {
        view.showSuccess();
    }

    @Override
    public void FailCall(String errorMsg) {
         view.showFailedLoginMsg(errorMsg);
    }

    @Override
    public void signUp(String email, String password , String confirmpassword) {
        if(email.isEmpty())
        {
            view.showEmailNeeded();
        }
        else if(password.isEmpty()) {
            view.showPasswordNeeded();
        }else if(confirmpassword.isEmpty())
        {
            view.showConfirmationPasswordNeeded();
        }else if(!confirmpassword.equals(password))
        {
            view.showEqualPasswords();
        }
        else {
            repo.makeAddCall(email ,password ,false , this);
        }
    }

    @Override
    public void addToSharedPrefrence(String email, String password) {
        repo.addUser(email , password);
    }
}
