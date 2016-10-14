package com.assgn.mad.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assgn.mad.weatherapp.db.City;

import java.util.List;

/**
 * Homework 06
 * SavedCitiesAdapter.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class SavedCitiesAdapter extends
        RecyclerView.Adapter<SavedCitiesAdapter.ViewHolder> {

    private List<City> cities;
    // Store the context for easy access
    private Context mContext;

    public SavedCitiesAdapter(Context context, List<City> cities) {
        this.cities = cities;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSavedCityState;
        public TextView textViewSavedTemp;
        public TextView textViewSavedUpdatedDate;
        public ImageView imageViewFavouriteStar;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewSavedCityState = (TextView) itemView.findViewById(R.id.textViewSavedCityState);
            textViewSavedTemp = (TextView) itemView.findViewById(R.id.textViewSavedTemp);
            textViewSavedUpdatedDate = (TextView) itemView.findViewById(R.id.textViewSavedUpdatedDate);
            imageViewFavouriteStar = (ImageView) itemView.findViewById(R.id.imageViewFavouriteStar);
        }
    }

    @Override
    public SavedCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.saved_city_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SavedCitiesAdapter.ViewHolder holder, int position) {
        City city = cities.get(position);

        TextView textViewSavedCityState = holder.textViewSavedCityState;
        textViewSavedCityState.setText(city.getCity() + ", " + city.getCountry());

        TextView textViewSavedTemp = holder.textViewSavedTemp;
        textViewSavedTemp.setText(String.valueOf(city.getTemperature()));

        TextView textViewSavedUpdatedDate  = holder.textViewSavedUpdatedDate;
        textViewSavedUpdatedDate.setText(mContext.getResources().getString(R.string.text_view_updated_on, city.getDate()));

        ImageView imageViewFavouriteStar  = holder.imageViewFavouriteStar;
        //imageViewFavouriteStar.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
