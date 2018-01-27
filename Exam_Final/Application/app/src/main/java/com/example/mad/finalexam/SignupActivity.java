package com.example.mad.finalexam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad.finalexam.Utility.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends Activity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    ProgressDialog progressDialog;

    String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextFirstName = (EditText) findViewById(R.id.edit_text_first_name);
        editTextLastName = (EditText) findViewById(R.id.edit_text_last_name);
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    public void SignUpAction(View view) {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (firstName.equals("")) {
            Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
        } else if (lastName.equals("")) {
            Toast.makeText(SignupActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(SignupActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (!Util.isValidEmail(email)) {
            Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Please enter password with length > 6.", Toast.LENGTH_SHORT).show();
        } else {
            fullName = firstName + " " + lastName;
            MainActivity.isSignUpInProgress = true;
            progressDialog.show();
            MainActivity.firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = MainActivity.firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    MainActivity.rootRef.child("Users").child(userId).setValue(fullName);
                                    MainActivity.firebaseAuth.signOut();
                                    MainActivity.isSignUpInProgress = false;
                                    finish();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Error!!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
        }
    }

    public void CancelSignUpAction(View view) {
        finish();
    }
}
