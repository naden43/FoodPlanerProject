package com.example.food_planer.register.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_planer.MainActivity;
import com.example.food_planer.R;
import com.example.food_planer.register.presenter.Presenter;
import com.example.food_planer.login.view.LoginActivity;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    FirebaseAuth auth ;
    TextView emailTxt ;
    TextView passwordTxt;
    TextView confirmPasswordTxt;

    Button registerBtn ;

    SignInButton signInButton;

    GoogleSignInClient client ;

    Presenter presenter ;

    TextView loginRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        presenter = new Presenter(LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(this) , FireBaseAuth.getInstance(this)), this , this);

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmPasswordTxt = findViewById(R.id.confirmpassTxt);
        registerBtn = findViewById(R.id.btnRegister);
        loginRedirect = findViewById(R.id.registerNavigation);
        signInButton = findViewById(R.id.signUpButton);
        //FirebaseApp.initializeApp(RegisterActivity.this);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addToSharedPrefrence("admin" , "admin");
                presenter.signInByGoogle();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presenter.checkConnectivity()) {
                    String email = emailTxt.getText().toString().trim();
                    String password = passwordTxt.getText().toString().trim();
                    String confirmPassword = confirmPasswordTxt.getText().toString().trim();
                    presenter.signUp(email, password, confirmPassword);
                }
                else {
                    showDialog();
                }
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this ,LoginActivity.class));
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
    public void showConfirmationPasswordNeeded() {
        confirmPasswordTxt.setError("Confirmation cannot be empty");
    }

    @Override
    public void showEqualPasswords() {
        confirmPasswordTxt.setError("password does not match");
    }

    @Override
    public void showFailedLoginMsg(String errorMsg) {
        Toast.makeText(RegisterActivity.this, "register fail" + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
        finish();
    }

    public void showDialog()
    {
        Dialog dialog  = new Dialog(this);
        dialog.setContentView(R.layout.dialog2);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.custom_dialog));
        Button okBtn = dialog.findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}