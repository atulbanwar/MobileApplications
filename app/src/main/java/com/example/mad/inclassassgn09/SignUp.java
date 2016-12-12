package com.example.mad.inclassassgn09;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends Activity {
    EditText firstName, lastName, email, password, confirmPassword;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText) findViewById(R.id.editTextFirstName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    public void actionCancel(View view) {
        finish();
    }

    public void actionSignUp(View view) {
        String fName = String.valueOf(firstName.getText());
        String lName = String.valueOf(lastName.getText());
        String eml = String.valueOf(email.getText());
        String pass = String.valueOf(password.getText());
        String confPass = String.valueOf(confirmPassword.getText());

        if (fName.equals("") || lName.equals("") || eml.equals("") || pass.equals("") || confPass.equals("")) {
            Toast.makeText(this, "Please enter all inputs", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(confPass)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            Signup(eml, pass, fName, lName);
        }
    }

    public void Signup(String email, String password, String fname, String lname) {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("fname", fname)
                .add("lname", lname)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();

        MainActivity.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SignUp.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignUp.this, "Invalid Input Passed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    UserResponse userResponse = MainActivity.gson.fromJson(response.body().string(), UserResponse.class);

                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                    editor.putString(MainActivity.USER_OBJ, MainActivity.gson.toJson(userResponse));
                    editor.apply();

                    Intent intent = new Intent(SignUp.this, ChatScreen.class);
                    intent.putExtra(MainActivity.USER_OBJ, userResponse);
                    startActivity(intent);
                    finish();
                } else {
                    SignUp.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUp.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
