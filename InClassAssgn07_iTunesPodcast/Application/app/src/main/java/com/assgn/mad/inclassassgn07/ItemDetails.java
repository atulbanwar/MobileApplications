package com.assgn.mad.inclassassgn07;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class ItemDetails extends Activity implements GetImageAsyncTask.IPodcastImage{
    Podcast podcast;
    ImageView imageViewLargeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TextView txtViewTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView txtViewReleaseDate = (TextView) findViewById(R.id.textViewReleaseDate);
        imageViewLargeImage = (ImageView) findViewById(R.id.imageViewLargeImage);
        TextView txtViewSummary = (TextView) findViewById(R.id.textViewSummary);

        if (getIntent().getExtras() != null) {
            podcast = (Podcast) getIntent().getExtras().getSerializable(MainActivity.PODCAST_ITEM);

            txtViewTitle.setText(podcast.getTitle());

            java.text.SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String formattedDate = sdf.format(podcast.getReleaseDate());
            txtViewReleaseDate.setText(formattedDate);

            txtViewSummary.setText(podcast.getSummary());

            //Picasso.with(this).load(podcast.getLargeImageUrl()).into(imageViewLargeImage);
            new GetImageAsyncTask(this).execute(podcast.getLargeImageUrl());
        }
    }

    @Override
    public void setUpImage(Bitmap bitmap) {
        if (bitmap != null) {
            imageViewLargeImage.setImageBitmap(bitmap);
        }
    }
}
