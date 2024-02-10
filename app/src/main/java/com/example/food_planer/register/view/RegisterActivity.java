package com.example.food_planer.register.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_planer.MainActivity;
import com.example.food_planer.R;
import com.example.food_planer.register.presenter.Presenter;
import com.example.food_planer.login.view.LoginActivity;
import com.example.food_planer.model.Reposatory;
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

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity {

    FirebaseAuth auth ;
    TextView emailTxt ;
    TextView passwordTxt;
    TextView confirmPasswordTxt;

    Button registerBtn ;

    SignInButton signInButton;

    GoogleSignInClient client ;

    Presenter presenter ;


    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == RESULT_OK)
            {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                try {

                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken() , null);
                    auth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            auth  = FirebaseAuth.getInstance();
                            Toast.makeText(RegisterActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegisterActivity.this, "SignInFail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    TextView loginRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.FILENAME , Context.MODE_PRIVATE);
        presenter = new Presenter(Reposatory.getInstance(UserLocalDataSourceimpl.getInstance(sharedPreferences) , FireBaseAuth.getInstance(auth)), this);


        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        confirmPasswordTxt = findViewById(R.id.confirmpassTxt);
        registerBtn = findViewById(R.id.btnRegister);
        loginRedirect = findViewById(R.id.loginNavigate);
        signInButton = findViewById(R.id.signUpButton);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.client_id)).requestEmail().build();


                client = GoogleSignIn.getClient(RegisterActivity.this , signInOptions);

                FirebaseApp.initializeApp(RegisterActivity.this);
                presenter.addToSharedPrefrence("admin" , "admin");
                Intent intent = client.getSignInIntent();
                launcher.launch(intent);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();
                String confirmPassword = confirmPasswordTxt.getText().toString().trim();
                presenter.signUp(email , password , confirmPassword);
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this ,LoginActivity.class));
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
    }

}