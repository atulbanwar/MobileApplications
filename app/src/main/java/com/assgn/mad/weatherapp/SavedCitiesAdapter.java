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

    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<City> cities;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public SavedCitiesAdapter(Context context, List<City> cities) {
        this.cities = cities;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textViewSavedCityState;
        public TextView textViewSavedTemp;
        public TextView textViewSavedUpdatedDate;
        public ImageView imageViewFavouriteStar;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
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

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.saved_city_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SavedCitiesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        City city = cities.get(position);

        // Set item views based on your views and data model
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
