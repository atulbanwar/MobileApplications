package com.example.mad.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by atulb on 10/24/2016.
 */

public class AppAdapter extends ArrayAdapter<App> {
    Context context;
    ArrayList<App> objects;

    public AppAdapter(Context context, ArrayList<App> objects) {
        super(context, R.layout.app_list_item, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_list_item, parent, false);
            holder = new ViewHolder();
            holder.appName = (TextView) convertView.findViewById(R.id.text_view_app_name);
            holder.appPrice = (TextView) convertView.findViewById(R.id.text_view_app_price);
            holder.appImage = (ImageView) convertView.findViewById(R.id.image_view_app_image);
            holder.appPriceImage = (ImageView) convertView.findViewById(R.id.text_view_app_price_image);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        TextView appName = holder.appName;
        TextView appPrice = holder.appPrice;
        ImageView appImage = holder.appImage;
        ImageView appPriceImage = holder.appPriceImage;

        appName.setText(objects.get(position).getName());

        double price = objects.get(position).getPrice();
        appPrice.setText(context.getResources().getString(R.string.text_view_app_price_Value, price));

        Picasso.with(context)
                .load(objects.get(position).getImageUrl())
                .resize(40, 40)
                .centerCrop()
                .into(appImage);


        if (price >= 0 && price < 1.99) {
            appPriceImage.setBackgroundResource(R.drawable.price_low);
            //appPriceImage.setMinimumWidth(20);
            //appPriceImage.setMaxWidth(20);
        } else if (price >= 2 && price < 5.99) {
            appPriceImage.setBackgroundResource(R.drawable.price_medium);
            //appPriceImage.setMaxWidth(40);
        } else if (price >= 6) {
            appPriceImage.setBackgroundResource(R.drawable.price_high);
            //appPriceImage.setMaxWidth(60);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView appName;
        TextView appPrice;
        ImageView appImage;
        ImageView appPriceImage;
    }
}