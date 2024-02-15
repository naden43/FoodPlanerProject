package com.example.food_planer.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.food_planer.MainActivity;
import com.example.food_planer.login.view.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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
    private FireBaseAuth(FirebaseAuth auth )
    {
         this.auth = auth;
    }

    public FirebaseAuth getFireBaseAuth()
    {
        return auth;
    }


    public static FireBaseAuth getInstance(FirebaseAuth auth )
    {
        if(instance==null)
        {
            instance = new FireBaseAuth(auth);
        }

        return instance;
    }




    public void makeAcallAdd(String email, String password,boolean remember,NetworkCallback networkCallback) {

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


            @Override
            public void onSuccess(AuthResult authResult) {

                networkCallback.successCall(remember);
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

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


            @Override
            public void onSuccess(AuthResult authResult) {

                networkCallback.successCall(remember);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                networkCallback.FailCall(e.getMessage());
            }
        });

    }
}
