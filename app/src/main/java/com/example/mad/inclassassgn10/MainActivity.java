package com.example.mad.inclassassgn10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends FragmentActivity implements LoginFragment.LoginFragmentInterface, NewAccountFragment.SignupFragmentInterface {
    /* implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener */

    /*
    TextView textViewStatus;

    GoogleApiClient googleApiClient;

    SignInButton buttonSignIn;
    Button buttonSignOut;

    private static final String SIGN_IN_KEY = "SIGN_IN_KEY";
    private static final int RC_SIGN_IN = 9001;

    TextView textViewCondition;
    Button buttonSunny;
    Button buttonFoggy;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = rootRef.child("condition");
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new LoginFragment(), "login_fragment").commit();
        /*
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        textViewStatus = (TextView) findViewById(R.id.text_view_status);

        buttonSignIn = (SignInButton) findViewById(R.id.button_sign_in);
        buttonSignIn.setOnClickListener(this);

        buttonSignOut = (Button) findViewById(R.id.button_sign_out);
        buttonSignOut.setOnClickListener(this);

        textViewCondition = (TextView) findViewById(R.id.text_view_condition);

        buttonSunny = (Button) findViewById(R.id.button_sunny);
        buttonSunny.setOnClickListener(this);
        
        buttonFoggy = (Button) findViewById(R.id.button_foggy);
        buttonFoggy.setOnClickListener(this);

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textViewCondition.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
    }

    @Override
    public void goToCreateAccount() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new NewAccountFragment(), "new_account_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToExpenses() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new ExpenseAppFragment(), "expense_app_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToLogin() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    /*
    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                textViewStatus.setText(R.string.text_view_status_signed_out);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            textViewStatus.setText(String.format(getString(R.string.text_view_welcome_status), account != null ? account.getDisplayName() : null));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Log
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.button_sign_out:
                signOut();
                break;
            case R.id.button_sunny:
                sunny();
                break;
            case R.id.button_foggy:
                foggy();
                break;
        }
    }

    private void foggy() {
        conditionRef.setValue("Foggy");
    }

    private void sunny() {
        conditionRef.setValue("Sunny");
    }
    */
}
