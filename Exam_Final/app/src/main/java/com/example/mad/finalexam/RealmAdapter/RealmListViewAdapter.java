package com.example.mad.finalexam.RealmAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.mad.finalexam.POJO.PojoForJSONParsing;
import com.example.mad.finalexam.R;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class RealmListViewAdapter extends RealmBaseAdapter<PojoForJSONParsing> implements ListAdapter {

    private static class ViewHolder {
        TextView textViewTitle;
        ImageView imageViewThumb;
    }

    public RealmListViewAdapter(Context context, OrderedRealmCollection<PojoForJSONParsing> realmResults) {
        super(context, realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_view_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.text_view_title);
            viewHolder.imageViewThumb = (ImageView) convertView.findViewById(R.id.image_view_thumb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PojoForJSONParsing item = adapterData.get(position);
        viewHolder.textViewTitle.setText(item.getVariable1() + " " + item.getVariable2());
        //viewHolder.expenseCost.setText(context.getResources().getString(R.string.text_view_expense_cost, String.valueOf(item.getAmount())));
        Picasso.with(context).load(item.getVariable3()).into(viewHolder.imageViewThumb);
        return convertView;
    }
}