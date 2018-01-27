package com.assgn.mad.inclassassgn07;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by atulb on 10/3/2016.
 */
public class GetPodcastItemsAsyncTask extends AsyncTask<String, Void, ArrayList<Podcast>> {
    IPodcastData podcastData;

    public GetPodcastItemsAsyncTask(IPodcastData podcastData) {
        this.podcastData = podcastData;
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return PodcastUtil.PodcastPullParser.parsePodcastItems(inputStream);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
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
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
        super.onPostExecute(podcasts);

        Collections.sort(podcasts, new Comparator<Podcast>() {
            @Override
            public int compare(Podcast p1, Podcast p2) {
                return p1.getReleaseDate().compareTo(p2.getReleaseDate());
            }
        });

        podcastData.setUpPodcastData(podcasts);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        podcastData.startPodcastFetchProgress();
    }

    public static interface IPodcastData {
        public void setUpPodcastData(ArrayList<Podcast> podcasts);
        public void startPodcastFetchProgress();
    }
}