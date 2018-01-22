package com.example.mad.inclassassgn09;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {
    private EditText editTextEmail;
    private EditText editTextPassword;

    public static ProgressDialog progressDialog;
    public static final String USER_OBJ = "USER_OBJ";
    public static SharedPreferences sharedPreferences;

    public static OkHttpClient client = new OkHttpClient();
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    public static Gson gson = gsonBuilder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PrettyTime pt = new PrettyTime();
        //Log.d("demo", pt.format(new Date()));

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /*
        messsageRepository.Login("user@test.com", "test");
        messsageRepository.Signup("a2@xyz.com", "123456", "f1", "l1");
        messsageRepository.GetFile("slJfe6i", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        messsageRepository.GetMessages("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        messsageRepository.AddNewMessages("TEXT", "comment", "", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        */

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        String userResponseStr = sharedPreferences.getString(MainActivity.USER_OBJ, "");
        if (!userResponseStr.isEmpty()) {
            UserResponse userResponse = gson.fromJson(userResponseStr, UserResponse.class);

            Intent intent = new Intent(MainActivity.this, ChatScreen.class);
            intent.putExtra(MainActivity.USER_OBJ, userResponse);
            startActivity(intent);
        }
    }

    public void actionSignIn(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.equals("")) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            Login(email, password);
        }
    }

    public void Login(String email, String password) {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Invalid Credentails", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MainActivity.progressDialog.dismiss();

                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    UserResponse userResponse = gson.fromJson(responseStr, UserResponse.class);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_OBJ, MainActivity.gson.toJson(userResponse));
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, ChatScreen.class);
                    intent.putExtra(MainActivity.USER_OBJ, userResponse);
                    startActivity(intent);
                } else {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Invalid Credentails", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void actionSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
