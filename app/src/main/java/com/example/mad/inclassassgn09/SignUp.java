package com.example.mad.inclassassgn09;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class SignUp extends Activity {


    private TextView firstName, lastName, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void actionCancel(){

    }

    public void actionSignUpSecond(){

    }

}
