package com.example.mad.inclassassgn09;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ChatScreen extends Activity implements MesssageRepository.IData{

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson;
    SharedPreferences sharedPreferences;
    ArrayList<Message> messages;
    ListView lv;
    MesssageRepository mr;
    UserResponse userResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        lv = (ListView) findViewById(R.id.listViewChat);
        mr = new MesssageRepository(this, this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = gsonBuilder.create();
        userResponse = gson.fromJson(sharedPreferences.getString("USER_AUTH", null), UserResponse.class);
        mr.GetMessages(userResponse.getToken());
    }

    public void actionSend()
    {

       
    }

    public void actionAddPhoto()
    {

    }

    @Override
    public void LoginResponse(UserResponse userResponse) {

    }

    @Override
    public void SignupResponse(UserResponse userResponse) {

    }

    @Override
    public void GetFileResponse(Bitmap btmp) {

    }

    @Override
    public void GetMessagesResponse(MessagesResponse messagesResponse) {

        messages.addAll(messagesResponse.getMessages());

        if (messages.size() > 0) {
            ChatListAdapter cla= new ChatListAdapter(ChatScreen.this, messages);
            cla.setNotifyOnChange(true);
            lv.setAdapter(cla);
        }else
        {
            Toast.makeText(ChatScreen.this, "no messages in chat room", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void AddMessageResponse(MessageResponse messageResponse) {

    }
}
