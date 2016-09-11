package com.homework.mad.expenseapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;


public class AddExpense extends Activity implements DatePickerDialog.OnDateSetListener {
    Calendar calendar;
    EditText edtTxtDate;
    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expense = new Expense();
        calendar = Calendar.getInstance(TimeZone.getDefault());
        edtTxtDate = (EditText) findViewById(R.id.edit_text_date);
    }

    public void showCalendar(View view) {
        DatePickerDialog dialog = new DatePickerDialog(AddExpense.this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(year, month, day);
        expense.setDate(calendar.getTime());
        edtTxtDate.setText(DateFormat.format("MM-dd-yyyy", calendar.getTime()).toString());
    }
}
