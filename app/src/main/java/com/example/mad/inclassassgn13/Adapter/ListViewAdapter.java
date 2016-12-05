package com.example.mad.inclassassgn13.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.mad.inclassassgn13.Pojo.User;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by atulb on 12/5/2016.
 */

public class ListViewAdapter extends RealmBaseAdapter<User> implements ListAdapter {

    private static class ViewHolder {
        TextView timestamp;
    }

    public ListViewAdapter(Context context, OrderedRealmCollection<User> realmResults) {
        super(context, realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.timestamp = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User item = adapterData.get(position);
        viewHolder.timestamp.setText(item.getName());
        return convertView;
    }
}