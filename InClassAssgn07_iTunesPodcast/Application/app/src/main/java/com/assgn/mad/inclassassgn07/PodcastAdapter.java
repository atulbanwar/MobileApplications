package com.assgn.mad.inclassassgn07;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by atulb on 10/3/2016.
 */

public class PodcastAdapter extends ArrayAdapter<Podcast> {
    Context context;
    ArrayList<Podcast> objects;

    public PodcastAdapter(Context context, ArrayList<Podcast> objects) {
        super(context, R.layout.item_row_layout, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Need to use inflator all the time, as reusing views from pool will make unmatched search results also 'Green' as the code below
        // on scroll will try to use views from the pool which might belong to the search matched views.

        /*
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_row_layout, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.textViewTitle);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        ImageView thumbnail = holder.thumbnail;
        TextView title = holder.title;

        Picasso.with(context).load(objects.get(position).getSmallImageUrl()).into(thumbnail);
        title.setText(objects.get(position).getTitle());

        return convertView;
        */

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_row_layout, parent, false);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.textViewTitle);

        Picasso.with(context).load(objects.get(position).getSmallImageUrl()).into(thumbnail);
        title.setText(objects.get(position).getTitle());

        if (objects.get(position).isMatched()) {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView title;
    }
}

