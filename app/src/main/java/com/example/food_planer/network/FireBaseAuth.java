package com.example.food_planer.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.food_planer.MainActivity;
import com.example.food_planer.NavigationActivity;
import com.example.food_planer.R;
import com.example.food_planer.login.view.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class FireBaseAuth {
    FirebaseAuth auth ;

    static private FireBaseAuth instance = null;
    GoogleSignInClient client ;


    boolean status = false;

    String errorMsg = null;
    GoogleSignInOptions signInOptions;

    FragmentActivity activity;

    ActivityResultLauncher<Intent> launcher;


    private FireBaseAuth(FragmentActivity activity)
    {
        this.activity = activity;
    }

    public FirebaseAuth getFireBaseAuth()
    {
        return auth;
    }


    public static FireBaseAuth getInstance(FragmentActivity activity)
    {
        if(instance==null)
        {
            instance = new FireBaseAuth(activity);
        }

        return instance;
    }

    public void intializeLauncher(GoogleCallBack googleCallBack)
    {
        launcher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode() == activity.RESULT_OK)
                {
                    Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                    try {

                        GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken() , null);
                        auth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                auth  = FirebaseAuth.getInstance();
                                String userId = auth.getCurrentUser().getUid();
                                googleCallBack.successSignIn( auth.getCurrentUser().getEmail() , userId);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                googleCallBack.FailCall(e.getMessage());
                                //Toast.makeText(LoginActivity.this, "SignInFail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void siginByGoogle(NetworkCallback networkCallback)
    {
        auth = FirebaseAuth.getInstance();
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.client_id)).requestEmail().build();
        client = GoogleSignIn.getClient(activity.getApplicationContext(),signInOptions);
        Intent intent = client.getSignInIntent();
        launcher.launch(intent);
    }




    public void makeAcallAdd(String email, String password,boolean remember,NetworkCallback networkCallback) {

        auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


            @Override
            public void onSuccess(AuthResult authResult) {

                networkCallback.successCall(remember , auth.getCurrentUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                networkCallback.FailCall(e.getMessage());
            }
        });

    }

    public void makeSignInCall(String email, String password,boolean remember,NetworkCallback networkCallback) {

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


            @Override
            public void onSuccess(AuthResult authResult) {

                networkCallback.successCall(remember , auth.getCurrentUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                networkCallback.FailCall(e.getMessage());
            }
        });

    }

    public String getUserId()
    {
        return auth.getCurrentUser().getUid();
    }
}
