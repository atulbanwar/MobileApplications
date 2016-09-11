package com.homework.mad.expenseapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Expense> expenses;

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
        intent.putExtra(EXPENSE_OBJS_KEY, expenses);
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
                expenses = (ArrayList<Expense>) data.getExtras().getSerializable(EXPENSE_OBJS_KEY);
            } else if (requestCode == REQ_CODE_DELETE) {
                expenses = (ArrayList<Expense>) data.getExtras().getSerializable(EXPENSE_OBJS_KEY);
            }
        } else if (resultCode == RESULT_CANCELED) {
            //DO Nothing
        }
    }

    public static Bitmap getBitmapFromUri(Uri uri, ContentResolver cr) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = cr.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
