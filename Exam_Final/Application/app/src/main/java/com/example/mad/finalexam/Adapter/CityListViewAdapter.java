package com.example.mad.finalexam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad.finalexam.POJO.City;
import com.example.mad.finalexam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityListViewAdapter extends ArrayAdapter<City> {
    Context context;
    ArrayList<City> objects;

    public CityListViewAdapter(Context context, ArrayList<City> objects) {
        super(context, R.layout.list_view_item_city, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_view_item_city, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.text_view_title);

        title.setText(objects.get(position).getVariable2().toString());

        /*
        if (objects.get(position).isMatched()) {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        */

        return convertView;
    }

    static class ViewHolder {
        TextView title;
    }
}

