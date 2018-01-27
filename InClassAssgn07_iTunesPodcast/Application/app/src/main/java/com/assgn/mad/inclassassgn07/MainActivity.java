package com.assgn.mad.inclassassgn07;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements GetPodcastItemsAsyncTask.IPodcastData {
    EditText edtTxtSearch;
    ListView listViewPodcastItems;
    ProgressDialog progressDialog;
    PodcastAdapter podcastAdapter;

    ArrayList<Podcast> podcasts;

    public static final String PODCAST_ITEM = "PODCAST_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTxtSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewPodcastItems = (ListView) findViewById(R.id.listViewPodCastItems);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading News...");

        new GetPodcastItemsAsyncTask(this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml");
    }

    @Override
    public void startPodcastFetchProgress() {
        progressDialog.show();
    }

    @Override
    public void setUpPodcastData(ArrayList<Podcast> podcasts) {
        progressDialog.dismiss();

        this.podcasts = podcasts;

        podcastAdapter = new PodcastAdapter(this, podcasts);
        podcastAdapter.setNotifyOnChange(true);
        listViewPodcastItems.setAdapter(podcastAdapter);

        listViewPodcastItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemDetails.class);
                intent.putExtra(PODCAST_ITEM, MainActivity.this.podcasts.get(position));
                startActivity(intent);
            }
        });
    }

    public void actionGo(View view) {
        String searchText = edtTxtSearch.getText().toString();

        ArrayList<Podcast> tempPodCasts = new ArrayList<Podcast>();
        tempPodCasts.addAll(podcasts);

        Podcast podcast;
        ArrayList<Podcast> matchedPodcasts = new ArrayList<>();
        ArrayList<Podcast> unmatchedPodcasts = new ArrayList<>();

        if (!searchText.isEmpty()) {
            for (int i = 0; i < podcasts.size(); i++) {
                podcast = podcasts.get(i);

                if (podcast.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    podcast.setMatched(true);
                    matchedPodcasts.add(podcast);
                } else {
                    unmatchedPodcasts.add(podcast);
                }
            }

            podcastAdapter.clear();
            podcastAdapter.addAll(matchedPodcasts);
            podcastAdapter.addAll(unmatchedPodcasts);

            podcasts = tempPodCasts;
        }
    }

    public void actionClear(View view) {
        ArrayList<Podcast> tempPodCasts = new ArrayList<Podcast>();
        tempPodCasts.addAll(podcasts);

        for (int i = 0; i < tempPodCasts.size(); i++) {
            tempPodCasts.get(i).setMatched(false);
        }

        podcastAdapter.clear();
        podcastAdapter.addAll(tempPodCasts);

        /*
        for (int i = 0; i < listViewPodcastItems.getChildCount(); i++) {
            listViewPodcastItems.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }*/
    }
}
