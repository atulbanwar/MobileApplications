package com.mad.chatmessenger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mad.chatmessenger.R;
import com.mad.chatmessenger.Utility.Util;
import com.mad.chatmessenger.firebase.FirebaseService;

public class ChatActivity extends AppCompatActivity {
    private String currentUserId;
    private String peerUserId;
    private String usersChatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getIntent().getExtras() != null) {
            peerUserId = getIntent().getExtras().getString("");

            currentUserId = FirebaseService.GetCurrentUser().getUid();
            peerUserId = "GR8mPbrmq3QvARTBguTt1nIe6of1";

            usersChatId = Util.getMergedId(currentUserId, peerUserId);
        }
    }
}
