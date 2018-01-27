package com.example.mad.midterm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.List;
/**
 * Created by atulb on 10/24/2016.
 */

public class SavedAppAdapter extends RecyclerView.Adapter<SavedAppAdapter.ViewHolder> {
    private List<App> apps;
    private Context context;
    private SharedPreferences sharedPreferences;

    public SavedAppAdapter(Context context, List<App> apps) {
        this.apps = apps;
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    private Context getContext() {
        return context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSavedAppState;
        public TextView textViewSavedTemp;
        public TextView textViewSavedUpdatedDate;
        public ImageView imageViewFavouriteStar;

        public ViewHolder(View itemView) {
            super(itemView);

            //textViewSavedAppState = (TextView) itemView.findViewById(R.id.textViewSavedAppState);
        }
    }

    @Override
    public SavedAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.saved_app_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SavedAppAdapter.ViewHolder holder, final int position) {
        App app = apps.get(position);

        //TextView textViewSavedAppState = holder.textViewSavedAppState;
        //textViewSavedAppState.setText(app.getApp() + ", " + app.getAppName());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

}
