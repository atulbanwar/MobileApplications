package com.assgn.mad.weatherapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assgn.mad.weatherapp.db.City;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Homework 06
 * SavedCitiesAdapter.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class SavedCitiesAdapter extends RecyclerView.Adapter<SavedCitiesAdapter.ViewHolder> {
    private List<City> cities;
    private Context context;

    public SavedCitiesAdapter(Context context, List<City> cities) {
        this.cities = cities;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

            imageViewFavouriteStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    City city = cities.get(getAdapterPosition());
                    boolean newFavourites = !city.isFavourite();
                    city.setFavourite(newFavourites);
                    MainActivity.dm.updateCity(city);

                    moveFavouritesOnTop();
                    notifyDataSetChanged();
                }
            });
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
    public void onBindViewHolder(SavedCitiesAdapter.ViewHolder holder, final int position) {
        City city = cities.get(position);

        TextView textViewSavedCityState = holder.textViewSavedCityState;
        textViewSavedCityState.setText(city.getCity() + ", " + city.getCountry());

        TextView textViewSavedTemp = holder.textViewSavedTemp;
        textViewSavedTemp.setText(String.valueOf(city.getTemperature()));

        TextView textViewSavedUpdatedDate  = holder.textViewSavedUpdatedDate;
        textViewSavedUpdatedDate.setText(context.getResources().getString(R.string.text_view_updated_on, city.getDate()));

        ImageView imageViewFavouriteStar  = holder.imageViewFavouriteStar;
        if (city.isFavourite()) {
            imageViewFavouriteStar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_gold));
        } else {
            imageViewFavouriteStar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_gray));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.dm.delete(cities.get(position));
                cities.remove(position);
                notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    private void moveFavouritesOnTop() {
        /*Collections.sort(cities, new Comparator<City>(){
            public int compare(City c1, City c2){
                return c1.getId() < c2.getId() ? -1 : 1;
            }
        });*/

        int favouriteIndex = 0;
        City city;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).isFavourite()) {
                city = cities.get(i);
                cities.remove(i);
                cities.add(favouriteIndex, city);
                favouriteIndex++;
                notifyDataSetChanged();
            }
        }
    }
}
