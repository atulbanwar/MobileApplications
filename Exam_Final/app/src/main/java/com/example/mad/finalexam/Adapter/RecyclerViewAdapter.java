package com.example.mad.finalexam.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad.finalexam.MainActivity;
import com.example.mad.finalexam.POJO.Place;
import com.example.mad.finalexam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Place> pojos;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Place> pojos) {
        this.pojos = pojos;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewThumb;
        public TextView textViewTitle;
        public ImageView imageViewAdd;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewThumb = (ImageView) itemView.findViewById(R.id.image_view_place_thumb);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_place_name);
            imageViewAdd = (ImageView) itemView.findViewById(R.id.image_view_add);

            imageViewAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Place pojo = pojos.get(getAdapterPosition());
                    MainActivity.currentTripRef.child("variable5").push().setValue(pojo);
                    /*
                    boolean newFavourites = !city.isFavourite();
                    city.setFavourite(newFavourites);

                    String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
                    if (pref_temp_type.equals("f")) {
                        Double newTemp = (city.getTemperature() - 32) / 1.8;
                        city.setTemperature(newTemp.intValue());
                        MainActivity.dm.updateCity(city);
                        newTemp = (city.getTemperature() * 1.8) + 32;
                        city.setTemperature(newTemp.intValue());
                    } else {
                        MainActivity.dm.updateCity(city);
                    }

                    moveFavouritesOnTop();
                    notifyDataSetChanged();
                    */
                }
            });
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Vertical
        View contactView = inflater.inflate(R.layout.recycler_view_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {
        Place pojo = pojos.get(position);

        ImageView imageViewThumb = holder.imageViewThumb;
        TextView textViewTitle = holder.textViewTitle;
        ImageView imageViewAdd = holder.imageViewAdd;


        textViewTitle.setText(pojo.getVariable3());

        /*
        TextView textViewSavedTemp = holder.textViewSavedTemp;
        String pref_temp_type = sharedPreferences.getString("preference_temperature_type", "");
        if (pref_temp_type.isEmpty() || pref_temp_type.equals("c")) {
            textViewSavedTemp.setText(context.getResources().getString(R.string.text_view_saved_temperature_celsius, String.valueOf(city.getTemperature())));
        } else {
            textViewSavedTemp.setText(context.getResources().getString(R.string.text_view_saved_temperature_fahrenhiet, String.valueOf(city.getTemperature())));
        }
        */

        /*
        ImageView imageViewFavouriteStar = holder.imageViewFavouriteStar;
        if (city.isFavourite()) {
            imageViewFavouriteStar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_gold));
        } else {
            imageViewFavouriteStar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.star_gray));
        }
        */

        Picasso.with(context).load(pojo.getVariable2()).resize(200, 200).centerCrop().into(imageViewThumb);

        /*holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //MainActivity.dm.delete(pojos.get(position));
                pojos.remove(position);
                notifyDataSetChanged();

                *//*
                if (pojos.size() == 0) {
                    ((TextView) ((Activity) context).findViewById(R.id.textViewSavedCitiesLable)).setVisibility(View.INVISIBLE);
                    ((TextView) ((Activity) context).findViewById(R.id.txtViewNoSavedCitiesId)).setVisibility(View.VISIBLE);
                }
                *//*

                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }
}