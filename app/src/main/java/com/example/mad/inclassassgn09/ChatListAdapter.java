package com.example.mad.inclassassgn09;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket on 10/31/2016.
 */

public class ChatListAdapter extends ArrayAdapter<Message> {

    private Context context;
    private ArrayList<Message> objects;
    public ChatListAdapter(Context context, int resource, ArrayList<Message> objects) {
        super(context, R.layout.listview_chat, objects);
        this.context = context;
        this.objects = objects;
    }


}
