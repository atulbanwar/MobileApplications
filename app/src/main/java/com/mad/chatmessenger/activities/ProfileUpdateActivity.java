package com.mad.chatmessenger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.chatmessenger.R;
import com.mad.chatmessenger.firebase.FirebaseService;
import com.mad.chatmessenger.model.User;
import com.squareup.picasso.Picasso;

public class ProfileUpdateActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText firstNameEditText, lastNameEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private User user=null;
    private String userId=FirebaseService.GetCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        user = new User();
        firstNameEditText = (EditText) findViewById(R.id.editTextFirstName);
        lastNameEditText = (EditText) findViewById(R.id.editTextLastName);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleRadioButton= (RadioButton) findViewById(R.id.radioButtonMale);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButtonFemale);
        profileImageView = (ImageView) findViewById(R.id.imageViewProfile);

        FirebaseService.getRootRef().child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if( user!=null)
                {
                    firstNameEditText.setText(user.getFirstName());
                    lastNameEditText.setText(user.getLastName());
                    if(user.getGender().equals(RegistrationActivity.MALE))
                    {
                        maleRadioButton.setChecked(true);
                        femaleRadioButton.setChecked(false);
                    }else
                    {
                        maleRadioButton.setChecked(false);
                        femaleRadioButton.setChecked(true);
                    }
                    Picasso.with(ProfileUpdateActivity.this).load(user.getImagePath()).into(profileImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void actionUpdate(View view) {
    }

    public void actionCancel(View view) {
        this.onBackPressed();
    }
}
