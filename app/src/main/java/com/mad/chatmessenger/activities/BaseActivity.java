package com.mad.chatmessenger.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mad.chatmessenger.R;
import com.mad.chatmessenger.firebase.FirebaseService;

public class BaseActivity extends AppCompatActivity {


    public static boolean userSignedIn = false;
    private FirebaseAuth auth= FirebaseService.getFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null && !MainActivity.isSignUpInProgress) {
                    userSignedIn=true;
                    Toast.makeText(BaseActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                    //TODO add activity reference later
                    Intent intentObj = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intentObj);

                }else {
                    Intent intentObj = new Intent(BaseActivity.this, MainActivity.class);
                    startActivity(intentObj);
                }
            }
        });

    }


}
