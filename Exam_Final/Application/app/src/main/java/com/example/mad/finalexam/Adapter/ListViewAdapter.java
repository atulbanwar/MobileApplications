package com.example.mad.finalexam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad.finalexam.POJO.PojoForJSONParsing;
import com.example.mad.finalexam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<PojoForJSONParsing> {
    Context context;
    ArrayList<PojoForJSONParsing> objects;

    public ListViewAdapter(Context context, ArrayList<PojoForJSONParsing> objects) {
        super(context, R.layout.list_view_item_1, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        /*
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item_1, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.image_view_thumb);
            holder.title = (TextView) convertView.findViewById(R.id.text_view_title);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        ImageView thumbnail = holder.thumbnail;
        TextView title = holder.title;

        Picasso.with(context).load(objects.get(position).getVariable2()).into(thumbnail);
        title.setText(objects.get(position).getVariable3().toString());

        return convertView;
        */

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_view_item_1, parent, false);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.image_view_thumb);
        TextView title = (TextView) convertView.findViewById(R.id.text_view_title);

        Picasso.with(context).load(objects.get(position).getVariable3()).resize(200, 200).centerCrop().into(thumbnail);
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
        ImageView thumbnail;
        TextView title;
    }
}

