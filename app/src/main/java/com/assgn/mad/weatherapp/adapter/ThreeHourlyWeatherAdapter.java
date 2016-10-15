package com.assgn.mad.weatherapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assgn.mad.weatherapp.R;
import com.assgn.mad.weatherapp.weatherdata.HourlyWeather;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Homework 06
 * ThreeHourlyWeatherAdapter.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class ThreeHourlyWeatherAdapter extends RecyclerView.Adapter<ThreeHourlyWeatherAdapter.ViewHolder> {

    private List<HourlyWeather> hourlyWeatherList;
    private SharedPreferences sharedPreferences;

    // Store the context for easy access
    private Context mContext;

    public ThreeHourlyWeatherAdapter(Context context, List<HourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
        mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }


    private Context getContext() {
        return mContext;
    }

    @Override
    public ThreeHourlyWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.three_hour_weather, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThreeHourlyWeatherAdapter.ViewHolder holder, int position) {
        HourlyWeather hourlyWeather = hourlyWeatherList.get(position);

        TextView tvTime = holder.textViewCurrentTime;
        TextView tvTemp = holder.textViewCurrentTemp;
        TextView tvCond = holder.textViewCurrentCondition;
        TextView tvPress = holder.textViewCurrentPressure;
        TextView tvHumd = holder.textViewCurrentHumidity;
        TextView tvWind = holder.textViewCurrentWind;
        ImageView ivIcon= holder.imageViewCurrentIconUrl;

        String temperature = String.valueOf(hourlyWeather.getTemperature());
        String pressure = String.valueOf(hourlyWeather.getPressure());
        String humidity = String.valueOf(hourlyWeather.getHumidity());
        String windSpeed = String.valueOf(hourlyWeather.getWindSpeed());
        String windDirectionDegree= String.valueOf(hourlyWeather.getWindDirectionDegree());
        String windDirection = hourlyWeather.getWindDirection();

        tvTime.setText(hourlyWeather.getTime());


        Picasso.with(mContext)
                .load(hourlyWeather.getIconUrl())
                .resize(300, 300)
                .centerCrop()
                .into(ivIcon);

        String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
        if (pref_temp_type.isEmpty() || pref_temp_type.equals("c")) {
            tvTemp.setText(mContext.getResources().getString(R.string.text_view_saved_temperature_celsius, String.valueOf(temperature)));
        } else {
            tvTemp.setText(mContext.getResources().getString(R.string.text_view_saved_temperature_fahrenhiet, String.valueOf(temperature)));
        }

        tvCond.setText(hourlyWeather.getCondition());
        tvPress.setText(String.format(getContext().getResources().getString(R.string.text_view_pressure_value),pressure));
        tvHumd.setText(String.format(getContext().getResources().getString(R.string.text_view_humidity_value),humidity)+"%");
        tvWind.setText(String.format(getContext().getResources().getString(R.string.text_view_winds_value),windSpeed,windDirectionDegree, windDirection));
    }

    @Override
    public int getItemCount() {
        if(hourlyWeatherList !=null)
            return hourlyWeatherList.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCurrentTime;
        public ImageView imageViewCurrentIconUrl;
        public TextView textViewCurrentTemp;
        public TextView textViewCurrentCondition;
        public TextView textViewCurrentPressure;
        public TextView textViewCurrentHumidity;
        public TextView textViewCurrentWind;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewCurrentTime = (TextView) itemView.findViewById(R.id.textViewTime);
            imageViewCurrentIconUrl = (ImageView) itemView.findViewById(R.id.imageViewCurrentIcon);
            textViewCurrentTemp = (TextView) itemView.findViewById(R.id.textViewTemperature);
            textViewCurrentCondition = (TextView) itemView.findViewById(R.id.textViewCondition);
            textViewCurrentPressure = (TextView) itemView.findViewById(R.id.textViewPressure);
            textViewCurrentHumidity = (TextView) itemView.findViewById(R.id.textViewHumidity);
            textViewCurrentWind = (TextView) itemView.findViewById(R.id.textViewWinds);



        }

    }
}
