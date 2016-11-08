package com.example.mad.inclassassgn10;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LoginFragment.LoginFragmentInterface, NewAccountFragment.SignupFragmentInterface {
    private FirebaseAuth firebaseAuth;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    String userId = user.getUid();
                    goToExpenses();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new LoginFragment(), "login_fragment").commit();
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
    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Incorrect Data Passed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

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
    public void signUp(String fullName, String email, String password) {
        this.fullName = fullName;

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();

                                UserExpense userExpense = new UserExpense();
                                userExpense.setUserName(MainActivity.this.fullName);
                                userExpense.setExpenses(new ArrayList<Expense>());

                                rootRef.child(userId)
                                        .push()
                                        .setValue(userExpense);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect Data Passed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
