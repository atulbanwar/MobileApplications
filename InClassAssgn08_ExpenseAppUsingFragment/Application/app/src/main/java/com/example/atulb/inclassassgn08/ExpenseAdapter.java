package com.example.atulb.inclassassgn08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by atulb on 10/17/2016.
 */

public class ExpenseAdapter  extends ArrayAdapter<Expense> {
    Context context;
    ArrayList<Expense> objects;

    public ExpenseAdapter(Context context, ArrayList<Expense> objects) {
        super(context, R.layout.expense_list_item, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expense_list_item, parent, false);
            holder = new ViewHolder();
            holder.expenseName = (TextView) convertView.findViewById(R.id.text_view_expense_name);
            holder.expensecost = (TextView) convertView.findViewById(R.id.text_view_expense_cost);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        TextView expenseName = holder.expenseName;
        TextView expenseCost = holder.expensecost;

        expenseName.setText(objects.get(position).getName());
        expenseCost.setText(context.getResources().getString(R.string.text_view_expense_cost, String.valueOf(objects.get(position).getAmount())));

        return convertView;
    }

    static class ViewHolder {
        TextView expenseName;
        TextView expensecost;
    }
}