package com.example.mad.finalexam;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mad.finalexam.Adapter.RecyclerViewAdapter;
import com.example.mad.finalexam.JSONParser.PlaceJSONParser;
import com.example.mad.finalexam.POJO.Place;
import com.example.mad.finalexam.POJO.Trip;
import com.example.mad.finalexam.Utility.GetPlaceDataTask;

import java.util.ArrayList;

public class AddPlaceActivity extends Activity implements GetPlaceDataTask.IJSONParsedData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Trip trip = null;
        if (getIntent().getExtras() != null) {
            trip = (Trip) getIntent().getExtras().getSerializable(MainActivity.KEY);
        }

        new GetPlaceDataTask(this).execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBePPk442JcZOZQs1jS6DzRM3wwNeeR5QI&location=" + trip.getVariable4() + "&radius=1000");
    }

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerViewName;
    @Override
    public void setupPlaceJSONParsedData(ArrayList<Place> result) {
        if (result != null) {
            recyclerViewName = (RecyclerView) findViewById(R.id.recycler_view_places);

            recyclerViewAdapter = new RecyclerViewAdapter(this, result);
            recyclerViewName.setAdapter(recyclerViewAdapter);
            recyclerViewName.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
