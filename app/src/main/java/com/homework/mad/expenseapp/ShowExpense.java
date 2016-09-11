package com.homework.mad.expenseapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowExpense extends Activity {

    private TextView txtVwName;
    private TextView txtVwCategory;
    private TextView txtVwAmount;
    private TextView txtVwDate;
    private ImageView imgVwReceipt;
    private ImageButton imgBtnFirst;
    private ImageButton imgBtnPrevious;
    private ImageButton getImgBtnNext;
    private ImageButton getImgBtnLast;
    private Button btnFinish;
    private ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        txtVwName = (TextView) findViewById(R.id.text_view_name_value);
        txtVwCategory = (TextView) findViewById(R.id.text_view_category_value);
        txtVwAmount = (TextView) findViewById(R.id.text_view_amount_value);
        txtVwDate = (TextView) findViewById(R.id.text_view_date_value);
        imgVwReceipt = (ImageView) findViewById(R.id.image_view_receipt);
        imgBtnFirst = (ImageButton) findViewById(R.id.image_button_first);
        imgBtnPrevious = (ImageButton)findViewById(R.id.image_button_previous);
        getImgBtnNext =  (ImageButton)findViewById(R.id.image_button_next);
        getImgBtnLast =  (ImageButton)findViewById(R.id.image_button_last);
        btnFinish = (Button) findViewById(R.id.button_finish);

        if (getIntent().getExtras() != null) {
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_OBJS_KEY);
        }

        displayContent(0);

    }

    private void displayContent(int position) {
        Expense expense= expenses.get(position);

        if( expense != null)
        {
            Uri imageUri = Uri.parse(expense.getReceipt());
            Bitmap bitmapImage=null;
            try {
                bitmapImage= getBitmapFromUri(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            txtVwName.setText(expense.getName());
            txtVwCategory.setText(expense.getCategory());
            txtVwAmount.setText("$ "+String.valueOf(expense.getAmount()));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            txtVwDate.setText(df.format(expense.getDate()));
            imgVwReceipt.setImageBitmap(bitmapImage);
            imgVwReceipt.setMaxHeight(500);
            imgVwReceipt.setMaxWidth(300);

        }
    }

    public Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void finish(View view) {
        finish();
    }
}
