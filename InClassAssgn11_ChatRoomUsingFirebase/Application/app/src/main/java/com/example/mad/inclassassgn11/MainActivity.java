package com.example.mad.inclassassgn11;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LoginFragment.LoginFragmentInterface, NewAccountFragment.SignupFragmentInterface, ExpenseAppFragment.ExpenseAppFragmentInterface, AddExpenseFragment.AddExpenseFragmentInterface, ShowExpensesFragment.ShowExpenseInterface {
    public static FirebaseAuth firebaseAuth;
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childRef;

    String fullName = "";
    ArrayList<Expense> expenses;
    int selectedItemPosition = -1;
    UserExpense userExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        userExpense = new UserExpense();

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    String userId = user.getUid();

                    childRef = rootRef.child(userId);
                    childRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userExpense = dataSnapshot.getValue(UserExpense.class);

                            if (userExpense != null) {
                                expenses = userExpense.getExpenses();
                                //fullName = userExpense.getUserName();

                                if (expenses == null) {
                                    expenses = new ArrayList<Expense>();
                                    userExpense.setExpenses(expenses);
                                }

                                goToExpenses();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
                            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
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

                                rootRef.child(userId).push();
                                rootRef.child(userId).setValue(userExpense);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already Exist.", Toast.LENGTH_SHORT).show();
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

    @Override
    public ArrayList<Expense> getExpenses() {
        return expenses == null ? new ArrayList<Expense>() : expenses;
    }

    @Override
    public void updateExpenses(ArrayList<Expense> expenses) {
        /*
        expenses = expenses;
        if (expenses.size() == 0) {
            userExpense.setUserName(fullName);
        }*/

        userExpense.setExpenses(expenses);
        childRef.setValue(userExpense);

        Toast.makeText(MainActivity.this, "Expense Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToAddExpense() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new AddExpenseFragment(), "add_expense_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addExpense(Expense expense) {
        userExpense.getExpenses().add(expense);
        childRef.setValue(userExpense);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelAddExpense() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void showExpense(int position) {
        selectedItemPosition = position;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new ShowExpensesFragment(), "show_expense_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Expense getExpense() {
        return expenses.get(selectedItemPosition);
    }

    @Override
    public void closeShowExpense() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }
}
