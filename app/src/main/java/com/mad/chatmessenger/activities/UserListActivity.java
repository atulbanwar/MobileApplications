package com.mad.chatmessenger.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.mad.chatmessenger.R;
import com.mad.chatmessenger.firebase.FirebaseService;
import com.mad.chatmessenger.model.User;
import com.squareup.picasso.Picasso;

public class UserListActivity extends MenuBaseActivity {

    private RecyclerView mRecyclerView;
    public static final String USER_ID = "user_id";
    private static String currentUSerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentUSerId=FirebaseService.GetCurrentUser().getUid();

    }


    @Override
    public void onBackPressed() {
        if (BaseActivity.userSignedIn) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }
                    }).create().show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, UserViewHolder> adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.user_list_recycler_layout,
                UserViewHolder.class,
                FirebaseService.getUSerListRef()
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, final User model, int position) {
                TextView fullNameTextView = viewHolder.fullName;
                ImageView thumbnailImageView = viewHolder.displayPicThumbnail;
                final View view = viewHolder.view;


                    fullNameTextView.setText(model.getFirstName() + " " + model.getLastName());


                    Picasso.with(UserListActivity.this).load(model.getImagePath()).into(thumbnailImageView);
                    thumbnailImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "Image Clicked", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), ProfileViewActivity.class);
                            intent.putExtra(USER_ID, model.getUserID());
                            startActivity(intent);
                        }
                    });
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "Item Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        };

        mRecyclerView.setAdapter(adapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView displayPicThumbnail;
        TextView fullName;

        public UserViewHolder(final View itemView) {
            super(itemView);
            displayPicThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
            fullName = (TextView) itemView.findViewById(R.id.textViewFullName);
            view = itemView;

        }
    }
}
