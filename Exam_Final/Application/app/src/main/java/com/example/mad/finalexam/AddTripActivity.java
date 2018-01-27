package com.example.mad.finalexam;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad.finalexam.Adapter.CityListViewAdapter;
import com.example.mad.finalexam.Adapter.ListViewAdapter;
import com.example.mad.finalexam.JSONParser.CityJSONParser;
import com.example.mad.finalexam.POJO.City;
import com.example.mad.finalexam.POJO.Trip;
import com.example.mad.finalexam.Utility.GetCityDataTask;
import com.example.mad.finalexam.Utility.GetGeoDataTask;

import java.util.ArrayList;
import java.util.Random;

import static com.example.mad.finalexam.MainActivity.rootRef;

public class AddTripActivity extends Activity implements GetCityDataTask.IJSONParsedData, GetGeoDataTask.IJSONParsedData {

    TextView textViewTripName;
    TextView textViewSearchCity;

    ListView listViewSearchedCities;
    CityListViewAdapter listViewAdapter;

    City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        textViewTripName = (TextView) findViewById(R.id.text_view_trip_name);
        textViewSearchCity  = (TextView) findViewById(R.id.text_view_city_search);

        listViewSearchedCities = (ListView) findViewById(R.id.list_view_searched_cities);

    }

    public void searchAction(View view) {
        String tripName = textViewTripName.getText().toString();
        String cityName = textViewSearchCity.getText().toString();

        if (tripName.equals("")) {
            Toast.makeText(AddTripActivity.this, "Please Enter Trip Name", Toast.LENGTH_SHORT).show();
        } else if (cityName.equals("")) {
            Toast.makeText(AddTripActivity.this, "Please Enter City Name to Search", Toast.LENGTH_SHORT).show();
        } else {
            new GetCityDataTask(this).execute("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyBePPk442JcZOZQs1jS6DzRM3wwNeeR5QI&types=(cities)&input=" + cityName);
        }
    }

    @Override
    public void setupCityJSONParsedData(final ArrayList<City> result) {
        if (result.size() != 0) {

            listViewAdapter = new CityListViewAdapter(this, result);
            listViewAdapter.setNotifyOnChange(true);

            listViewSearchedCities.setAdapter(listViewAdapter);
            listViewSearchedCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedCity = result.get(position);

                    new GetGeoDataTask(AddTripActivity.this).execute("https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyBePPk442JcZOZQs1jS6DzRM3wwNeeR5QI&placeid=" + selectedCity.getVariable3());

                }
            });
        } else {
            Toast.makeText(AddTripActivity.this, "No Cities Found. Try different name.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setupGeoJSONParsedData(String result) {
        if (!result.equals("")) {
            Trip trip = new Trip();
            Random rand = new Random();
            int  randomNum = rand.nextInt(50) + 1;

            trip.setVariable1(randomNum);
            trip.setVariable2(textViewTripName.getText().toString());
            trip.setVariable3(selectedCity.getVariable2());
            trip.setVariable4(result);

            rootRef.child("Trips").push().setValue(trip);

            finish();
        } else {
            Toast.makeText(AddTripActivity.this, "Unable to fetch GeoLocation of selected city", Toast.LENGTH_SHORT).show();
        }
    }
}