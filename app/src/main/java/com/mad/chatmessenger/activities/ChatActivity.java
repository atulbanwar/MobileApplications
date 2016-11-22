package com.mad.chatmessenger.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.chatmessenger.R;
import com.mad.chatmessenger.Utility.Util;
import com.mad.chatmessenger.firebase.FirebaseService;
import com.mad.chatmessenger.model.Message;
import com.mad.chatmessenger.model.User;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    private String currentUserId;
    private String currentUserName;

    private String peerUserId;
    private String peerUserName;

    private String currentChatId;

    private RecyclerView recyclerViewMessages;
    private TextView editTextPeerName;
    private EditText editTextMessageToSend;

    FirebaseRecyclerAdapter<Message, ChatActivity.MessageViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewMessages = (RecyclerView) findViewById(R.id.recycler_view_messages);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        editTextMessageToSend = (EditText) findViewById(R.id.edit_text_message_to_send);
        editTextPeerName = (TextView) findViewById(R.id.text_view_peer_name);

        if (getIntent().getExtras() != null) {
            currentUserId = FirebaseService.GetCurrentUser().getUid();
            peerUserId = getIntent().getExtras().getString(UserListActivity.USER_ID);

            //peerUserId = "GR8mPbrmq3QvARTBguTt1nIe6of1";

            currentChatId = Util.getMergedId(currentUserId, peerUserId);

            FirebaseService.getUSerListRef().child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User currentUser = dataSnapshot.getValue(User.class);
                    currentUserName = currentUser.getFirstName() + " " + currentUser.getLastName();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FirebaseService.getUSerListRef().child(peerUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User peerUser = dataSnapshot.getValue(User.class);
                    peerUserName = peerUser.getFirstName() + " " + peerUser.getLastName();
                    editTextPeerName.setText(peerUserName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                    Message.class,
                    R.layout.layout_chat_item,
                    ChatActivity.MessageViewHolder.class,
                    FirebaseService.getCurrentChatRef(currentChatId)
            ) {
                @Override
                protected void populateViewHolder(ChatActivity.MessageViewHolder viewHolder, Message model, final int position) {
                    if (model.getType().equals("TEXT")) {
                        viewHolder.chatMesssage.setText(model.getText());
                    } else {
                        Picasso.with(ChatActivity.this).load(model.getImageUrl()).into(viewHolder.chatImage);
                    }

                    if (model.getUserId().equals(currentUserId)) {
                        viewHolder.userName.setText(currentUserName);
                    } else {
                        viewHolder.userName.setText(peerUserName);
                    }

                    try {
                        viewHolder.timeFromNow.setText(Util.prettyDate(model.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    viewHolder.deleteMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.getRef(position).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            };

            recyclerViewMessages.setAdapter(adapter);
        }
    }

    public void actionSendTextMessage(View view) {
        String messageText = editTextMessageToSend.getText().toString();

        Message message = new Message();
        message.setId("1");
        message.setType("TEXT");
        message.setText(messageText);
        message.setUserId(currentUserId);
        message.setRead(false);
        message.setTime(Calendar.getInstance().getTime().toString());

        FirebaseService.getCurrentChatRef(currentChatId).push().setValue(message);

        editTextMessageToSend.setText("");
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView chatImage;
        TextView chatMesssage;
        TextView userName;
        TextView timeFromNow;
        ImageView deleteMessage;

        public MessageViewHolder(final View itemView) {
            super(itemView);
            chatImage = (ImageView) itemView.findViewById(R.id.image_view_chat_image);
            chatMesssage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            userName = (TextView) itemView.findViewById(R.id.text_view_user_name);
            timeFromNow = (TextView) itemView.findViewById(R.id.text_view_time_from_now);
            deleteMessage = (ImageView) itemView.findViewById(R.id.image_view_delete_message);
        }
    }
}
