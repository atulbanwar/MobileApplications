package com.example.mad.finalexam.RealmAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mad.finalexam.MainActivity;
import com.example.mad.finalexam.POJO.PojoForJSONParsing;
import com.example.mad.finalexam.R;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RlmRecyclerViewAdapter extends RealmRecyclerViewAdapter<PojoForJSONParsing, RlmRecyclerViewAdapter.MyViewHolder> {

    private final MainActivity activity;

    public RlmRecyclerViewAdapter(MainActivity activity, OrderedRealmCollection<PojoForJSONParsing> data) {
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
        PojoForJSONParsing obj = getData().get(position);
        holder.data = obj;
        holder.textViewTitle.setText(obj.getVariable1() + " " + obj.getVariable2());
        Picasso.with(context).load(obj.getVariable3()).into(holder.imageViewThumbnail);
    }

    // View.OnLongClickListener
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewTitle;
        public ImageView imageViewThumbnail;

        public PojoForJSONParsing data;

        public MyViewHolder(View view) {
            super(view);
            textViewTitle = (TextView) view.findViewById(R.id.text_view_title);
            imageViewThumbnail = (ImageView) view.findViewById(R.id.image_view_thumb);
            view.setOnClickListener(this);
            // setOnLongClickLis...
        }

        // onLongClick
        @Override
        public void onClick(View v) {
            //activity.deleteItem(getAdapterPosition());
        }
    }
}