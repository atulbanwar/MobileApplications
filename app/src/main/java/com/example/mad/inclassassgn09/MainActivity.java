package com.example.mad.inclassassgn09;

import android.app.Activity;
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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrettyTime pt = new PrettyTime();
        Log.d("demo", pt.format(new Date()));

        /* Asynchronous */
        OkHttpClient client = new OkHttpClient();

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

        /* Synchronous */
        /*
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("parameter");
        */
    }

    /* Synchronous */
    /*
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://www.reddit.com/all.json?limit=5")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }*/
}
