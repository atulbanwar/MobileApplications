package com.example.mad.inclass2b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

// Assignment 2b
// Sanket Patil
// Atul Banwar

public class MainActivity extends AppCompatActivity {
    private TextView length1, length2, areaValue;
    private Double doubleLength1, doubleLength2, areaCal;
    private RadioGroup rdGroupArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdGroupArea = (RadioGroup) findViewById(R.id.rdGroupArea);
        length1 = (TextView) findViewById(R.id.editTextLength1);
        length2 = (TextView) findViewById(R.id.editTextLength2);
        areaValue = (TextView) findViewById(R.id.txtAreaValue);
    }

    public void onClickCalculate(View v) {
        if (isInputValid()) {
            doubleLength1 = Double.parseDouble(length1.getText().toString());
            if (length2.getText().length() != 0) {
                doubleLength2 = Double.parseDouble(length2.getText().toString());
            }

            if (rdGroupArea.getCheckedRadioButtonId() == R.id.rdBtnTriangleArea) {
                areaCal = 0.5 * doubleLength1 * doubleLength2;
            } else if (rdGroupArea.getCheckedRadioButtonId() == R.id.rdBtnSquareArea) {
                areaCal = doubleLength1 * doubleLength1;
                length2.setText("");
            } else if (rdGroupArea.getCheckedRadioButtonId() == R.id.rdBtnCircleArea) {
                areaCal = 3.14 * doubleLength1 * doubleLength1;
                length2.setText("");
            } else if (rdGroupArea.getCheckedRadioButtonId() == R.id.rdBtnRectangleArea) {
                areaCal = doubleLength1 * doubleLength2;
            }
            areaValue.setText(areaCal.toString());
        }

        if (rdGroupArea.getCheckedRadioButtonId() == R.id.rdBtnClearAll) {
            length1.setText("");
            length2.setText("");
            areaValue.setText("");
        }
    }

    private boolean isInputValid() {
        boolean validInput1 = TextUtils.isDigitsOnly(length1.getText().toString());
        boolean validInput2 = TextUtils.isDigitsOnly(length2.getText().toString());

        if ((validInput1 == true && validInput2 == true) && (length2.getText().length() != 0 && length1.getText().length() != 0)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid input", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}