package com.example.mad.inclass05;
/*
    In Class 05
    Tempestt Swinson
    Nanda Kishore Kolluru
    Karthikeyan Thorali Krishnamurthy Ragunath
    Atul Banwar
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements DownloadImageTask.ImageData {
    Button go;
    EditText search;
    ImageView photoDisplay;
    ImageButton prev;
    ImageButton next;
    String baseUrl = "http://dev.theappsdr.com/apis/photos/index.php?keyword=";
    ProgressDialog pbar;
    ArrayList<String> urlList = new ArrayList<>();
    int urlIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go = (Button) findViewById(R.id.button_go);
        search = (EditText) findViewById(R.id.editText_search);
        photoDisplay = (ImageView) findViewById(R.id.photoDisplay);
        prev = (ImageButton) findViewById(R.id.prev);
        next = (ImageButton) findViewById(R.id.next);
        pbar = new ProgressDialog(this);
        pbar.setIndeterminate(true);
        prev.setEnabled(false);
        next.setEnabled(false);
    }

    //http://dev.theappsdr.com/apis/photos/index.php?keyword=
    public void goAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setItems(getResources().getStringArray(R.array.Options), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] items = getResources().getStringArray(R.array.Options);
                        search.setText(items[which]);
                        if (isConnectedOnline()) {
                            new GetDictionaryTask().execute(baseUrl + items[which]);
                        } else {
                            Toast.makeText(MainActivity.this, "No Internet Available", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.show();
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void setupData(Bitmap image) {
        photoDisplay.setImageBitmap(image);
    }

    @Override
    public void startProgress() {
        pbar.setMessage("Loading Photo...");
        pbar.show();
    }

    @Override
    public void stopProgress() {
        pbar.dismiss();
    }

    public void nextAction(View view) {
        if ((urlIndex + 1) == urlList.size()) {
            urlIndex = 0;
        } else {
            urlIndex++;
        }
        new DownloadImageTask(MainActivity.this).execute(urlList.get(urlIndex));
    }

    public void prevAction(View view) {
        if ((urlIndex - 1) < 0) {
            urlIndex = urlList.size() - 1;
        } else {
            urlIndex--;
        }
        new DownloadImageTask(MainActivity.this).execute(urlList.get(urlIndex));
    }

    private class GetDictionaryTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pbar.dismiss();
            Log.d("demo", s);
            StringTokenizer token = new StringTokenizer(s, ";");
            token.nextToken();
            urlList.clear();
            while (token.hasMoreTokens()) {
                urlList.add(token.nextToken());
            }
            if (urlList.size() > 0) {
                new DownloadImageTask(MainActivity.this).execute(urlList.get(0));
                if (urlList.size() > 1) {
                    prev.setEnabled(true);
                    next.setEnabled(true);
                }
            } else {
                //photoDisplay.invalidate();
                photoDisplay.setImageBitmap(null);
                Toast.makeText(MainActivity.this, "No Images Found", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbar.setMessage("Loading Dictionary...");
            pbar.show();
        }
    }
}
