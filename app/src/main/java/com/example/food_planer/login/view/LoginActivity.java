package com.example.food_planer.login.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_planer.NavigationActivity;
import com.example.food_planer.R;
import com.example.food_planer.dpfirestore.FireStoreRemoteDataSourceimpl;
import com.example.food_planer.model.MealLocalDataSourceimpl;
import com.example.food_planer.model.PlanMealLocalDataSourceimpl;
import com.example.food_planer.model.Reposatory;
import com.example.food_planer.network.FoodRemoteSourceImpl;
import com.example.food_planer.register.view.RegisterActivity;
import com.example.food_planer.login.presenter.Presenter;
import com.example.food_planer.model.LoginAndRegisterReposatory;
import com.example.food_planer.model.UserLocalDataSourceimpl;
import com.example.food_planer.network.FireBaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private static final String TAG = "LoginActivity";
    TextView emailTxt ;
    TextView passwordTxt;
    Button loginBtn ;
    TextView registerRedirect;
    SignInButton signInButton;
    CheckBox remember ;
    Presenter presenter;
    Button skip;

    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        Reposatory repo = Reposatory.getInstance(FoodRemoteSourceImpl.getInstance(), MealLocalDataSourceimpl.getInstance(getApplicationContext()), PlanMealLocalDataSourceimpl.getInstance(getApplicationContext()), FireStoreRemoteDataSourceimpl.getInstance());
        presenter = new Presenter(LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(this) , FireBaseAuth.getInstance(this)), this , repo);

        presenter.intializeLauncher();
        presenter.checkSavedLogin();


        FirebaseApp.initializeApp(this);

        emailTxt = findViewById(R.id.txtEmail);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.btnLogin);
        registerRedirect = findViewById(R.id.registerNavigation);
        signInButton = findViewById(R.id.signInButton);
        remember = findViewById(R.id.rememberMe);
        skip = findViewById(R.id.btnSkip);
        constraintLayout = findViewById(R.id.background);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.addToSharedPrerefrence("admin" , "admin");
                presenter.sigInByGoogle();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                presenter.signIn(email , password , remember.isChecked());
            }
        });

        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , NavigationActivity.class));
                finish();
            }
        });

    }

    @Override
    public void showEmailNeeded() {
        emailTxt.setError("please enter email");
    }

    @Override
    public void showPasswordNeeded() {
        passwordTxt.setError("please enter password");
    }

    @Override
    public void showFaildLoginMsg(String errorMsg) {
        Toast.makeText(LoginActivity.this, "fail to sign in " + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Snackbar snackbar = Snackbar.make(constraintLayout , "Hello in your way to eat delicies meals"  ,Snackbar.LENGTH_LONG);
        snackbar.show();
        startActivity(new Intent(LoginActivity.this , NavigationActivity.class));
        finish();
    }

    @Override
    public void goToHomePage() {
        startActivity(new Intent(this  , NavigationActivity.class));
        finish();
    }
}