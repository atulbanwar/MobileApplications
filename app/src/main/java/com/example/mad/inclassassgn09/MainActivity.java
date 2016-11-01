package com.example.mad.inclassassgn09;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements MesssageRepository.IData {
    MesssageRepository messsageRepository;
    public static final String USER_AUTH = "USER_AUTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrettyTime pt = new PrettyTime();
        Log.d("demo", pt.format(new Date()));

        messsageRepository = new MesssageRepository(this, getApplicationContext());
        messsageRepository.Login("user@test.com", "test");

        messsageRepository.Signup("a1@xyz.com", "123456", "f1", "l1");

        messsageRepository.GetFile("slJfe6i", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");

        messsageRepository.GetMessages("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");

        messsageRepository.AddNewMessages("TEXT", "comment", "", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0Nzc2Mjc3MTAsImV4cCI6MTUwOTE2MzcxMCwianRpIjoiMXhhWkdBV1o3RkxpNWpYZ0RiS0dTSSIsInVzZXIiOjJ9.dKLFNjt1Uz-2cOsGYFrfMH_XfoJZSJuGK3qqMt9NgQA");
    }

    @Override
    public void LoginResponse(UserResponse userResponse) {

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
}
