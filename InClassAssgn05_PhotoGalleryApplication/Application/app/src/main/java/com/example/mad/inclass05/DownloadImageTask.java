package com.example.mad.inclass05;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 In Class 05
 Tempestt Swinson
 Nanda Kishore Kolluru
 Karthikeyan Thorali Krishnamurthy Ragunath
 Atul Banwar
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageData activity;

    public DownloadImageTask(ImageData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.startProgress();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setupData(bitmap);
        activity.stopProgress();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public interface ImageData {
        public void setupData(Bitmap image);
        public void startProgress();
        public void stopProgress();
    }
}