package com.assgn.mad.inclassassgn07;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by atulb on 10/3/2016.
 */
public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    IPodcastImage podcastImage;

    //TriviaActivity triviaActivity;
    public GetImageAsyncTask(IPodcastImage podcastImage) {
        this.podcastImage = podcastImage;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setRequestMethod("GET");
            con.connect();
            int status_code = con.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                int response = con.getResponseCode();
                InputStream in = con.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        try {
            podcastImage.setUpImage(bitmap);
        } catch (Exception e) {
            Log.d("demo", e.toString());
        }
        super.onPostExecute(bitmap);
    }

    public static interface IPodcastImage {
        public void setUpImage(Bitmap bitmap);
    }

}
