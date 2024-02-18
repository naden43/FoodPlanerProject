package com.example.food_planer.register.presenter;

import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.network.GoogleCallBack;
import com.example.food_planer.network.NetworkCallback;
import com.example.food_planer.register.view.IRegisterActivity;

public class Presenter implements IPresenter , NetworkCallback , GoogleCallBack {

    LoginAndRegisterReposatory repo ;

    IRegisterActivity view ;

    String email ;
    String password;
    public Presenter(LoginAndRegisterReposatory repo , IRegisterActivity view )
    {
        this.repo = repo;
        this.view = view;
    }




    @Override
    public void successSignIn(String email, String userId) {
        view.showSuccess();
        repo.addUser(email , "" , userId);
    }

    @Override
    public void successCall(boolean remember, String userId) {

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

    }


    @Override
    public void intializeLauncher() {
        repo.intializeLauncher(this);
    }

    @Override
    public void signInByGoogle() {
        repo.sigInByGoogle(this , this);
    }
}
