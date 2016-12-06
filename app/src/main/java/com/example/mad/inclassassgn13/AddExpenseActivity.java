package com.example.mad.inclassassgn13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mad.inclassassgn13.Pojo.Expense;

import java.util.Date;
import java.util.Random;

import io.realm.Realm;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtTxtExpenseName;
    private Spinner spnrCategory;
    private EditText edtTxtAmount;
    private Realm realm;
    private Expense expense;
    private int currentExpenseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        edtTxtExpenseName = (EditText) findViewById(R.id.editTextExpenseName);
        spnrCategory = (Spinner) findViewById(R.id.spinnerCategory);
        edtTxtAmount = (EditText) findViewById(R.id.editTextAmount);
        realm = Realm.getDefaultInstance();
    }

    public void actionAdd(View view) {
        String expenseName = edtTxtExpenseName.getText().toString();
        String category = spnrCategory.getSelectedItem().toString();
        double amount = Double.parseDouble(edtTxtAmount.getText().toString().length() != 0 ? edtTxtAmount.getText().toString() : "0");
        if (expenseName.length() == 0) {
            Toast.makeText(AddExpenseActivity.this, getResources().getString(R.string.error_empty_expense_name), Toast.LENGTH_SHORT).show();
        } else if (category.equals("Select Category")) {
            Toast.makeText(AddExpenseActivity.this, getResources().getString(R.string.error_empty_category), Toast.LENGTH_SHORT).show();
        } else if (amount == 0) {
            Toast.makeText(AddExpenseActivity.this, getResources().getString(R.string.error_empty_amount), Toast.LENGTH_SHORT).show();
        } else {

            Random rand = new Random();
            int  randomNum = rand.nextInt(50) + 1;

            saveIntoDatabase(expenseName, category, amount, randomNum);
        }
    }

    private void saveIntoDatabase(final String expenseName, final String category, final double amount, final int randomNum) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                expense = bgRealm.createObject(Expense.class);
                expense.setName(expenseName);
                expense.setCategory(category);
                expense.setAmount(amount);
                expense.setExpenseId(randomNum);
                expense.setDate(new Date().toString());
                currentExpenseId=randomNum;
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(AddExpenseActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(AddExpenseActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(AddExpenseActivity.this, ShowExpenseActivity.class);
        intent.putExtra("EXPENSE_SHOW", currentExpenseId);
        startActivity(intent);
    }

    public void actionCancel(View view) {
        finish();
    }


}
