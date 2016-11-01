package com.example.mad.inclassassgn09;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sanket on 10/31/2016.
 */

public class ChatListAdapter extends ArrayAdapter<Message>{

    private Context context;
    private ArrayList<Message> objects;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    public ChatListAdapter(Context context, ArrayList<Message> objects) {
        super(context, R.layout.listview_chat, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_chat, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.timeFrom = (TextView) convertView.findViewById(R.id.textViewTime);
            holder.layoutContainer = (LinearLayout) convertView.findViewById(R.id.linearLayoutContainer);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        TextView textName = holder.name;
        TextView textTimeFrom = holder.timeFrom;
        LinearLayout layoutContainer = holder.layoutContainer;

        Message message = objects.get(position);

        if(message.getType()=="IMAGE")
        {
            TextView chatMessage = new TextView(context);
            chatMessage.setText(message.getComment());
            layoutContainer.addView(chatMessage);

        }else if(message.getType()=="IMAGE")
        {
            final ImageView image = new ImageView(context);

            OkHttpClient client = new OkHttpClient();

            GsonBuilder gsonBuilder = new GsonBuilder();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            gson = gsonBuilder.create();
            UserResponse userResponse = gson.fromJson(sharedPreferences.getString("USER_AUTH", null), UserResponse.class);
            Request request = new Request.Builder()
                    .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/" + message.getFileThumbnailId())
                    .header("Authorization", "BEARER " + userResponse.getToken())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context, "Invalid Id.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        InputStream responseStr = response.body().byteStream();
                        Bitmap btmp = BitmapFactory.decodeStream(responseStr);
                        image.setImageBitmap(btmp);
                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        textName.setText(message.getUserFname()+" "+message.getUserLname());
        try {
            textTimeFrom.setText(TimeUtility.prettyDate(message.getCreatedAt()));
        } catch (Exception e) {
            textTimeFrom.setText("");
        }

        return convertView;

    }
    static class ViewHolder {
        TextView name;
        TextView timeFrom;
        LinearLayout layoutContainer;
    }

}
