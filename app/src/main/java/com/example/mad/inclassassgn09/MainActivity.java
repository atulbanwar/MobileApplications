package com.example.mad.inclassassgn09;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.Date;

public class MainActivity extends Activity implements MesssageRepository.IData {
    MesssageRepository messsageRepository;
    public static final String USER_AUTH = "USER_AUTH";

    EditText editTextEmail;
    EditText editTextPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrettyTime pt = new PrettyTime();
        Log.d("demo", pt.format(new Date()));

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        messsageRepository = new MesssageRepository(this, getApplicationContext());

        /*
        messsageRepository.Login("user@test.com", "test");
        messsageRepository.Signup("a2@xyz.com", "123456", "f1", "l1");
        messsageRepository.GetFile("slJfe6i", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        messsageRepository.GetMessages("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        messsageRepository.AddNewMessages("TEXT", "comment", "", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
        */

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    public void LoginResponse(UserResponse userResponse) {
        progressDialog.dismiss();
        if (userResponse != null) {
            Intent intent = new Intent(this, ChatScreen.class);
            startActivity(intent);
        } else {
            //Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void SignupResponse(UserResponse userResponse) {
    }

    @Override
    public void GetFileResponse(Bitmap btmp) {

    }

    @Override
    public void GetMessagesResponse(MessagesResponse messagesResponse) {

    }

    @Override
    public void AddMessageResponse(MessageResponse messageResponse) {

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
            messsageRepository.Login(email, password);
        }
    }

    public void actionSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
