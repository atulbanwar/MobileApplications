package com.example.mad.inclassassgn13.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.mad.inclassassgn13.Pojo.Expense;
import com.example.mad.inclassassgn13.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by atulb on 12/5/2016.
 */

public class ListViewAdapter extends RealmBaseAdapter<Expense> implements ListAdapter {

    private static class ViewHolder {
        TextView expenseName;
        TextView expenseCost;
    }

    public ListViewAdapter(Context context, OrderedRealmCollection<Expense> realmResults) {
        super(context, realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expense_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.expenseName = (TextView) convertView.findViewById(R.id.text_view_expense_name);
            viewHolder.expenseCost = (TextView) convertView.findViewById(R.id.text_view_expense_cost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expense item = adapterData.get(position);
        viewHolder.expenseName.setText(item.getName());
        viewHolder.expenseCost.setText(context.getResources().getString(R.string.text_view_expense_cost, String.valueOf(item.getAmount())));
        return convertView;
    }
}