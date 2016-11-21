package com.mad.chatmessenger.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.chatmessenger.R;
import com.mad.chatmessenger.firebase.FirebaseService;
import com.mad.chatmessenger.model.User;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ProfileUpdateActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText firstNameEditText, lastNameEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private User user=null;
    private String userId=FirebaseService.GetCurrentUser().getUid();
    private String firstName, lastName, gender;
    private ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(this);

        FirebaseService.getRootRef().child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if( user!=null)
                {
                    progressDialog.show();
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


                    Picasso.with(ProfileUpdateActivity.this)
                            .load(user.getImagePath())
                            .into(profileImageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onError() {

                                }
                            });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void actionUpdate(View view) {

        firstName=firstNameEditText.getText().toString();
        lastName=lastNameEditText.getText().toString();
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();

        if(selectedId == maleRadioButton.getId()) {
            gender=RegistrationActivity.MALE;

        } else if(selectedId == femaleRadioButton.getId()) {
            gender=RegistrationActivity.FEMALE;
        } else {
            Toast.makeText(this, "Please select A Gender!", Toast.LENGTH_SHORT).show();
        }

        if(checkForUpdate() && validateForm())
        {
            user.setUserID(userId);
            user.setGender(gender);
            user.setFirstName(firstName);
            FirebaseService.getRootRef().child("Users").child(userId).setValue(user);
        }


    }

    private boolean checkForUpdate() {
        if( user.getGender() == gender && user.getFirstName()==firstName && user.getLastName() ==lastName)
        {
            return false;
        }else
        {
            return true;
        }

    }

    public void actionCancel(View view) {
        this.onBackPressed();
    }


    /**
     * Validates all form elements
     * @return
     */
    private boolean validateForm() {
        if (firstName.equals("")) {
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (lastName.equals("")) {
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
