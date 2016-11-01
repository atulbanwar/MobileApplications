package com.example.mad.inclassassgn09;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends Activity implements MesssageRepository.IData {
    MesssageRepository messsageRepository;
    EditText firstName, lastName, email, password, confirmPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        messsageRepository = new MesssageRepository(this, getApplicationContext());

        firstName = (EditText) findViewById(R.id.editTextFirstName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    public void actionCancel(View view) {
        finish();
    }

    public void actionSignUpSecond(View view) {
        String fName = String.valueOf(firstName.getText());
        String lName = String.valueOf(lastName.getText());
        String eml = String.valueOf(email.getText());
        String pass = String.valueOf(password.getText());
        String confPass = String.valueOf(confirmPassword.getText());

        if (fName.equals("") || lName.equals("") || eml.equals("") || pass.equals("") || confPass.equals("")) {
            Toast.makeText(this, "Please enter all inputs", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(confPass)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            messsageRepository.Signup(eml, pass, fName, lName);
        }
    }

    @Override
    public void LoginResponse(UserResponse userResponse) {

    }

    @Override
    public void SignupResponse(UserResponse userResponse) {
        progressDialog.dismiss();
        if (userResponse != null) {
            Intent intent = new Intent(this, ChatScreen.class);
            startActivity(intent);
        }
    }

    @Override
    public void GetFileResponse(Bitmap btmp) {

    }

    @Override
    public void GetMessagesResponse(MessagesResponse messagesResponse) {

    }

    @Override
    public void AddMessageResponse(MessageResponse messageResponse) {

    }
}
