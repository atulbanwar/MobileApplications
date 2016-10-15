package com.assgn.mad.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assgn.mad.weatherapp.R;
import com.assgn.mad.weatherapp.db.City;
import com.assgn.mad.weatherapp.weatherdata.DailyWeather;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Homework 06
 * DayWeatherSummaryAdapter.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class DayWeatherSummaryAdapter extends
        RecyclerView.Adapter<DayWeatherSummaryAdapter.ViewHolder> {

    private List<DailyWeather> dailyWeathers;

    // Store the context for easy access
    private Context mContext;

    private OnItemClickListener mItemClickListener;

    public DayWeatherSummaryAdapter(Context context, List<DailyWeather> dailyWeathers) {
        this.dailyWeathers = dailyWeathers;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewDate;
        public TextView textViewTemp;
        public ImageView imageViewMedianIconUrl;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewTemp = (TextView) itemView.findViewById(R.id.textViewTemp);
            imageViewMedianIconUrl = (ImageView) itemView.findViewById(R.id.imageViewMedianIcon);
            itemView.setOnClickListener(this);  //Bind your setOnClickLister Here on ViewHolder creation
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    //Method to initialize ItemCLickListner
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public DayWeatherSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.daywise_summary, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DayWeatherSummaryAdapter.ViewHolder holder, int position) {
        DailyWeather dailyWeather = dailyWeathers.get(position);

        TextView textViewSavedCityState = holder.textViewDate;
        textViewSavedCityState.setText(dailyWeather.getDate());

        TextView textViewSavedTemp = holder.textViewTemp;
        textViewSavedTemp.setText(String.valueOf(dailyWeather.getMedianTemprature()));

        ImageView imageViewFavouriteStar = holder.imageViewMedianIconUrl;

        Picasso.with(mContext)
                .load(dailyWeather.getMedianImageUrl())
                .resize(300, 300)
                .centerCrop()
                .into(imageViewFavouriteStar);
        //imageViewFavouriteStar.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        if (dailyWeathers != null)
            return dailyWeathers.size();
        else
            return 0;
    }
}