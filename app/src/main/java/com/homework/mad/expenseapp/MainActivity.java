package com.homework.mad.expenseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Expense> expenses;

    private Button btnAddExpense;
    private Button btnEditExpense;
    private Button btnDeleteExpense;
    private Button btnFinishActivity;
    private Intent intent;

    public static final int REQ_CODE_ADD = 1;
    public static final int REQ_CODE_EDIT = 2;
    public static final int REQ_CODE_DELETE = 3;

    public static final String EXPENSE_OBJ_KEY = "EXPENSE";
    public static final String EXPENSE_OBJS_KEY = "EXPENSES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenses = new ArrayList<Expense>();
        btnAddExpense = (Button) findViewById(R.id.button_add_expense);
        btnEditExpense = (Button) findViewById(R.id.button_edit_expense);
        btnDeleteExpense = (Button) findViewById(R.id.button_delete_expense);
        btnFinishActivity = (Button) findViewById(R.id.button_finish);
    }

    public void addExpense(View view) {
        intent = new Intent(MainActivity.this, AddExpense.class);
        startActivityForResult(intent, REQ_CODE_ADD);
    }

    public void editExpense(View view) {
        intent = new Intent(MainActivity.this, EditExpense.class);
        intent.putExtra(EXPENSE_OBJS_KEY, expenses);
        startActivityForResult(intent, REQ_CODE_EDIT);
    }

    public void deleteExpense(View view) {
        intent = new Intent(MainActivity.this, DeleteExpense.class);
        //intent.putExtra();
        startActivityForResult(intent, REQ_CODE_DELETE);
    }

    public void showExpense(View view) {
        intent = new Intent(MainActivity.this, ShowExpense.class);
        intent.putExtra(EXPENSE_OBJS_KEY, expenses);
        startActivity(intent);
    }

    public void finish(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Expense expense;

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE_ADD) {
                expense = (Expense) data.getExtras().getSerializable(EXPENSE_OBJ_KEY);
                expenses.add(expense);
            } else if (requestCode == REQ_CODE_EDIT) {
                // TODO: Implement Logic
            } else if (requestCode == REQ_CODE_DELETE) {
                // TODO: Implement Logic
            }
        } else if (resultCode == RESULT_CANCELED) {
            //DO Nothing
        }
    }
}
