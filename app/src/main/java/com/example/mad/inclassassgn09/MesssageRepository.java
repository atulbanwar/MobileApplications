package com.example.mad.inclassassgn09;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by atulb on 10/31/2016.
 */

public class MesssageRepository {
    OkHttpClient client;

    public void Login() {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("demo", response.body().string());
            }
        });
    }
}
