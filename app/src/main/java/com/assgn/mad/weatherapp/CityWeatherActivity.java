package com.assgn.mad.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.assgn.mad.weatherapp.adapter.DayWeatherSummaryAdapter;
import com.assgn.mad.weatherapp.utils.WaitFor5Seconds;
import com.assgn.mad.weatherapp.weatherdata.DailyWeather;
import com.assgn.mad.weatherapp.weatherdata.GetWeatherForecastAsyncTask;

import java.util.ArrayList;

public class CityWeatherActivity extends AppCompatActivity implements GetWeatherForecastAsyncTask.IData, WaitFor5Seconds.IData{

    private ProgressDialog progressDialog;
    private ArrayList<DailyWeather> dayWeatherItems;
    private  RecyclerView dayWeatherRV;
    private  LinearLayoutManager horizontalLayoutManager;
    private TextView textViewDailyForecastLocationValue;

    private String cityName;
    private String stateInitials;
    private String hourlyForcaseURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        textViewDailyForecastLocationValue = (TextView) findViewById(R.id.textViewCityCountry) ;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.msg_progress_dialog_loading));
        progressDialog.setIndeterminate(true);
        dayWeatherRV = (RecyclerView) findViewById(R.id.rvDaySummary);
        dayWeatherRV.setAdapter(new DayWeatherSummaryAdapter(this,null));

        horizontalLayoutManager = new LinearLayoutManager(CityWeatherActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManager.setSmoothScrollbarEnabled(true);
        dayWeatherRV.setLayoutManager(horizontalLayoutManager);

        if (getIntent().getExtras() != null) {
            cityName = getIntent().getExtras().getString(MainActivity.CITY);
            stateInitials = getIntent().getExtras().getString(MainActivity.STATE);
            hourlyForcaseURL = getIntent().getExtras().getString(MainActivity.CITY_WEATHER_URL);

            textViewDailyForecastLocationValue.setText(getResources().getString(R.string.text_view_daily_forecast_value, cityName, stateInitials));
            new GetWeatherForecastAsyncTask(this).execute("http://api.openweathermap.org/data/2.5/forecast?q=London,UK&mode=json&appid=c145e85ff53d712456d2a094f94e404f");
        }

    }

    @Override
    public void startDataFetch() {
        progressDialog.show();
    }

    @Override
    public void setupData(ArrayList<DailyWeather> result) {

        progressDialog.dismiss();

        if (result.size() > 0) {
            dayWeatherItems = result;

            DayWeatherSummaryAdapter dayWeatherAdapter = new DayWeatherSummaryAdapter(this, dayWeatherItems);
           /* DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenWidth= metrics.widthPixels;
            dayWeatherRV.getLayoutParams().width = screenWidth/;*/
            dayWeatherRV.setAdapter(dayWeatherAdapter);
            dayWeatherRV.setLayoutManager(horizontalLayoutManager);
        } else {
            Toast.makeText(CityWeatherActivity.this, getResources().getString(R.string.msg_no_matches), Toast.LENGTH_SHORT).show();
            new WaitFor5Seconds(this).execute("");
        }

    }

    private int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
