package com.assgn.mad.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityUnitTestCase;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.assgn.mad.weatherapp.adapter.DayWeatherSummaryAdapter;
import com.assgn.mad.weatherapp.adapter.OnItemClickListener;
import com.assgn.mad.weatherapp.adapter.ThreeHourlyWeatherAdapter;
import com.assgn.mad.weatherapp.db.City;
import com.assgn.mad.weatherapp.utils.WaitFor5Seconds;
import com.assgn.mad.weatherapp.weatherdata.DailyWeather;
import com.assgn.mad.weatherapp.weatherdata.GetWeatherForecastAsyncTask;
import com.assgn.mad.weatherapp.weatherdata.HourlyWeather;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Homework 06
 * CityWeatherActivity.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class CityWeatherActivity extends Activity implements GetWeatherForecastAsyncTask.IData, WaitFor5Seconds.IData {

    private ProgressDialog progressDialog;
    private ArrayList<DailyWeather> dayWeatherItems;
    private RecyclerView dayWeatherRV;
    private LinearLayoutManager horizontalLayoutManagerOne;
    private LinearLayoutManager horizontalLayoutManagerTwo;
    private SharedPreferences sharedPreferences;

    DayWeatherSummaryAdapter dayWeatherAdapter;
    ThreeHourlyWeatherAdapter tHWA;

    private RecyclerView hourlyWeatherRV;
    private TextView textViewDailyForecastLocationValue;
    private TextView textViewDateValue;

    private String cityName;
    private String stateInitials;
    private String hourlyForcaseURL;

    public static boolean isTemperatureSettingUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        textViewDailyForecastLocationValue = (TextView) findViewById(R.id.textViewCityCountry);
        textViewDateValue = (TextView) findViewById(R.id.textViewDateValue);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.msg_progress_dialog_loading));
        progressDialog.setIndeterminate(true);

        dayWeatherRV = (RecyclerView) findViewById(R.id.rvDaySummary);
        hourlyWeatherRV = (RecyclerView) findViewById(R.id.rvHourSummary);

        horizontalLayoutManagerOne = new LinearLayoutManager(CityWeatherActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManagerOne.setSmoothScrollbarEnabled(true);

        horizontalLayoutManagerTwo = new LinearLayoutManager(CityWeatherActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManagerTwo.setSmoothScrollbarEnabled(true);


        dayWeatherRV.setLayoutManager(horizontalLayoutManagerOne);
        dayWeatherAdapter = new DayWeatherSummaryAdapter(this, null);
        dayWeatherRV.setAdapter(dayWeatherAdapter);

        hourlyWeatherRV.setLayoutManager(horizontalLayoutManagerTwo);
        tHWA = new ThreeHourlyWeatherAdapter(this, null);
        hourlyWeatherRV.setAdapter(tHWA);

        if (getIntent().getExtras() != null) {
            cityName = getIntent().getExtras().getString(MainActivity.CITY);
            stateInitials = getIntent().getExtras().getString(MainActivity.STATE);
            hourlyForcaseURL = getIntent().getExtras().getString(MainActivity.CITY_WEATHER_URL);

            textViewDailyForecastLocationValue.setText(getResources().getString(R.string.text_view_daily_forecast_value, cityName, stateInitials));
            new GetWeatherForecastAsyncTask(this).execute(hourlyForcaseURL);
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
            setTemperatureAsPerType();

            dayWeatherAdapter = new DayWeatherSummaryAdapter(this, dayWeatherItems);

            dayWeatherRV.setLayoutManager(horizontalLayoutManagerOne);
            dayWeatherRV.setAdapter(dayWeatherAdapter);

            dayWeatherAdapter.SetOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    tHWA = new ThreeHourlyWeatherAdapter(CityWeatherActivity.this, dayWeatherItems.get(position).getHourlyWeathers());
                    textViewDateValue.setText(dayWeatherItems.get(position).getDate());
                    hourlyWeatherRV.setLayoutManager(horizontalLayoutManagerTwo);
                    hourlyWeatherRV.setAdapter(tHWA);
                }
            });

            tHWA = new ThreeHourlyWeatherAdapter(this, dayWeatherItems.get(0).getHourlyWeathers());
            textViewDateValue.setText(dayWeatherItems.get(0).getDate());
            hourlyWeatherRV.setLayoutManager(horizontalLayoutManagerTwo);
            hourlyWeatherRV.setAdapter(tHWA);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.city_weather_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.city_weather_settings_menu) {
            Intent preferenceActivity = new Intent(CityWeatherActivity.this, PreferenceActivity.class);
            startActivity(preferenceActivity);
        } else if (item.getItemId() == R.id.city_weather_save_city_menu) {
            City city = new City();
            city.setCity(cityName);
            city.setCountry(stateInitials);

            city.setTemperature((int) dayWeatherItems.get(0).getMedianTemprature());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            city.setDate(dateFormat.format(calendar.getTime()));
            city.setFavourite(false);

            String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
            Double newTemp;
            if (pref_temp_type.equals("f")) {
                newTemp = (city.getTemperature() - 32) / 1.8;
                city.setTemperature(newTemp.intValue());
            }

            City cityObj = MainActivity.dm.getCity(cityName, stateInitials);
            if (cityObj == null) {
                MainActivity.dm.saveCity(city);
                Toast.makeText(CityWeatherActivity.this, getResources().getString(R.string.msg_city_saved), Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.dm.updateCity(city);
                Toast.makeText(CityWeatherActivity.this, getResources().getString(R.string.msg_city_updated), Toast.LENGTH_SHORT).show();
            }

            if (pref_temp_type.equals("f")) {
                newTemp = (city.getTemperature() * 1.8) + 32;
                city.setTemperature(newTemp.intValue());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTemperatureOnTypeUpdate();
    }

    private void setTemperatureAsPerType() {
        String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");

        if (pref_temp_type.equals("f")) {
            for (int i = 0; i < dayWeatherItems.size(); i++) {
                DailyWeather dailyWeatherForUpdate = dayWeatherItems.get(i);
                Double newTemp = (dailyWeatherForUpdate.getMedianTemprature() * 1.8) + 32;
                dailyWeatherForUpdate.setMedianTemprature(Double.parseDouble(new DecimalFormat("#.##").format(newTemp)));

                for (int j = 0; j < dailyWeatherForUpdate.getHourlyWeathers().size(); j++) {
                    HourlyWeather hourlyWeatherForUpdate = dailyWeatherForUpdate.getHourlyWeathers().get(j);
                    Double newHourTemp = (hourlyWeatherForUpdate.getTemperature() * 1.8) + 32;
                    hourlyWeatherForUpdate.setTemperature(Double.parseDouble(new DecimalFormat("#.##").format(newHourTemp)));
                }
            }
        }
    }

    private void setTemperatureOnTypeUpdate() {
        String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
        if (isTemperatureSettingUpdated) {
            isTemperatureSettingUpdated = false;

            if (pref_temp_type.equals("c")) {
                for (int i = 0; i < dayWeatherItems.size(); i++) {
                    DailyWeather dailyWeatherForUpdate = dayWeatherItems.get(i);
                    Double newTemp = (dailyWeatherForUpdate.getMedianTemprature() - 32) / 1.8;
                    dailyWeatherForUpdate.setMedianTemprature(Double.parseDouble(new DecimalFormat("#.##").format(newTemp)));

                    for (int j = 0; j < dailyWeatherForUpdate.getHourlyWeathers().size(); j++) {
                        HourlyWeather hourlyWeatherForUpdate = dailyWeatherForUpdate.getHourlyWeathers().get(j);
                        Double newHourTemp = (hourlyWeatherForUpdate.getTemperature() - 32) / 1.8;
                        hourlyWeatherForUpdate.setTemperature(Double.parseDouble(new DecimalFormat("#.##").format(newHourTemp)));
                    }
                }
            } else if (pref_temp_type.equals("f")) {
                for (int i = 0; i < dayWeatherItems.size(); i++) {
                    DailyWeather dailyWeatherForUpdate = dayWeatherItems.get(i);
                    Double newTemp = (dailyWeatherForUpdate.getMedianTemprature() * 1.8) + 32;
                    dailyWeatherForUpdate.setMedianTemprature(Double.parseDouble(new DecimalFormat("#.##").format(newTemp)));

                    for (int j = 0; j < dailyWeatherForUpdate.getHourlyWeathers().size(); j++) {
                        HourlyWeather hourlyWeatherForUpdate = dailyWeatherForUpdate.getHourlyWeathers().get(j);
                        Double newHourTemp = (hourlyWeatherForUpdate.getTemperature() * 1.8) + 32;
                        hourlyWeatherForUpdate.setTemperature(Double.parseDouble(new DecimalFormat("#.##").format(newHourTemp)));
                    }
                }
            }

            dayWeatherAdapter.notifyDataSetChanged();
            tHWA.notifyDataSetChanged();
        }
    }
}
