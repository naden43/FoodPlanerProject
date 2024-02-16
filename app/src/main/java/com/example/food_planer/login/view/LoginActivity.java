package com.example.food_planer.login.view;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_planer.NavigationActivity;
import com.example.food_planer.R;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private static final String TAG = "LoginActivity";
    public static final String FILENAME = "rememberFile";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    FirebaseAuth auth ;
    TextView emailTxt ;
    TextView passwordTxt;
    Button loginBtn ;
    TextView registerRedirect;

    SignInButton signInButton;

    GoogleSignInClient client ;

    CheckBox remember ;

    SharedPreferences sharedPreferences ;

    Presenter presenter;

    // should be in activity how can i make it in another file
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
                            Toast.makeText(LoginActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(LoginActivity.this, "SignInFail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(FILENAME ,Context.MODE_PRIVATE);
        presenter = new Presenter(LoginAndRegisterReposatory.getInstance(UserLocalDataSourceimpl.getInstance(sharedPreferences) , FireBaseAuth.getInstance(auth)), this);

        presenter.checkSavedLogin();


        FirebaseApp.initializeApp(this);

        emailTxt = findViewById(R.id.txtEmail);
        passwordTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.btnLogin);
        registerRedirect = findViewById(R.id.registerNavigation);
        signInButton = findViewById(R.id.signInButton);
        remember = findViewById(R.id.rememberMe);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)).requestEmail().build();


        client = GoogleSignIn.getClient(LoginActivity.this , signInOptions);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addToSharedPrerefrence("admin" , "admin");
                Intent intent = client.getSignInIntent();
                launcher.launch(intent);
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

        Button skip = findViewById(R.id.btnSkip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + "clicked");
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
        Toast.makeText(LoginActivity.this, "signing in successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this , NavigationActivity.class));
        finish();
    }

    @Override
    public void goToHomePage() {
        startActivity(new Intent(this  , NavigationActivity.class));
        finish();
    }
}