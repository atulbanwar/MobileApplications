package com.example.mad.inclassassgn09;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MesssageRepository {
    OkHttpClient client;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson;
    SharedPreferences sharedPreferences;
    IData activity;
    Context appContext;
    

    public MesssageRepository(IData activity, Context context) {
        this.activity = activity;
        appContext = context;
    }

    public void Login(String email, String password) {
        client = new OkHttpClient();

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
                Toast.makeText(appContext, "Invalid Credentails", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(MainActivity.USER_AUTH, responseStr);
                    editor.apply();

                    gson = gsonBuilder.create();
                    UserResponse userResponse = gson.fromJson(responseStr, UserResponse.class);
                    activity.LoginResponse(userResponse);
                } else {
                    activity.LoginResponse(null);
                }
            }
        });
    }

    public void Signup(final String email, final String password, final String fname, final String lname) {
        client = new OkHttpClient();

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(appContext, "Invalid data passed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    gson = gsonBuilder.create();
                    UserResponse userResponse = gson.fromJson(response.body().string(), UserResponse.class);
                    userResponse.setUserFname(fname);
                    userResponse.setUserLname(lname);
                    userResponse.setUserEmail(email);

                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(MainActivity.USER_AUTH, gson.toJson(userResponse));
                    editor.apply();

                    activity.SignupResponse(userResponse);
                } else {
                    activity.SignupResponse(null);
                }
            }
        });
    }

    public void GetFile(String fileId, String token) {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/" + fileId)
                .header("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(appContext, "Invalid Id.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream responseStr = response.body().byteStream();
                    Bitmap btmp = BitmapFactory.decodeStream(responseStr);

                    activity.GetFileResponse(btmp);
                } else {
                    activity.GetFileResponse(null);
                }
            }
        });
    }

    public void GetMessages(String token) {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/messages")
                .header("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(appContext, "Invalid Id.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    gson = gsonBuilder.create();
                    String messagesJson = response.body().string();
                    MessagesResponse messagesResponse = gson.fromJson(messagesJson, MessagesResponse.class);

                    activity.GetMessagesResponse(messagesResponse);
                } else {
                    activity.GetMessagesResponse(null);
                }
            }
        });
    }

    public void AddNewMessages(String Type, String Comment, String FileThumbnailID, String token) {
        client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Type", Type)
                .add("Comment", Comment)
                .add("FileThumbnailID", FileThumbnailID)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/message/add")
                .header("Authorization", "BEARER " + token)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(appContext, "Invalid data passed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    gson = gsonBuilder.create();
                    MessageResponse messageResponse = gson.fromJson(response.body().string(), MessageResponse.class);

                    activity.AddMessageResponse(messageResponse);
                } else {
                    activity.AddMessageResponse(null);
                }
            }
        });
    }

    static public interface IData {
        public void LoginResponse(UserResponse userResponse);
        public void SignupResponse(UserResponse userResponse);
        public void GetFileResponse(Bitmap btmp);
        public void GetMessagesResponse(MessagesResponse messagesResponse);
        public void AddMessageResponse(MessageResponse messageResponse);
    }
}