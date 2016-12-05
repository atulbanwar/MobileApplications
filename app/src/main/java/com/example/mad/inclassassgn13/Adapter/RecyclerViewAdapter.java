package com.example.mad.inclassassgn13.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mad.inclassassgn13.MainActivity;
import com.example.mad.inclassassgn13.Pojo.User;
import com.example.mad.inclassassgn13.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by atulb on 12/5/2016.
 */

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<User, RecyclerViewAdapter.MyViewHolder> {

    private final MainActivity activity;

    public RecyclerViewAdapter(MainActivity activity, OrderedRealmCollection<User> data) {
        //super(data, true);
        super(activity, data, true);
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User obj = getData().get(position);
        holder.data = obj;
        holder.title.setText(obj.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView title;
        public User data;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_view_title);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            activity.deleteItem(data);
            return true;
        }
    }
}