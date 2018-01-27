package com.example.mad.finalexam.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    IDownloadImageTask activity;

    public DownloadImageTask(IDownloadImageTask activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.startDownloadImageTaskProgress();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setupImageData(bitmap);
        activity.stopDownloadImageTaskProgress();
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

    static public interface IDownloadImageTask {
        public void setupImageData(Bitmap image);
        public void startDownloadImageTaskProgress();
        public void stopDownloadImageTaskProgress();
    }
}