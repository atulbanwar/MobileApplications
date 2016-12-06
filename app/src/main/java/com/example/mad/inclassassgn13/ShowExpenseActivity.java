package com.example.mad.inclassassgn13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mad.inclassassgn13.Pojo.Expense;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowExpenseActivity extends AppCompatActivity {

    private Expense expense;
    private TextView txtViewName;
    private TextView txtViewCategory;
    private TextView txtViewAmount;
    private TextView txtViewDate;
    private Button btnClose;
    private Realm realm;
    private Expense currentExpense;
    private int recievedExpenseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        txtViewName = (TextView) findViewById(R.id.text_view_name_value);
        txtViewCategory = (TextView) findViewById(R.id.text_view_category_value);
        txtViewAmount = (TextView) findViewById(R.id.text_view_amount_value);
        txtViewDate = (TextView) findViewById(R.id.text_view_date_value);
        btnClose = (Button) findViewById(R.id.button_close);
        realm = Realm.getDefaultInstance();

        if (savedInstanceState != null) {
            recievedExpenseId = savedInstanceState.getInt("EXPENSE_SHOW");
        }else
        {
            Bundle bundle= getIntent().getExtras();
            recievedExpenseId = bundle.getInt("EXPENSE_SHOW");
        }

        RealmResults<Expense> results = realm.where(Expense.class)
                .findAll();

        for(Expense expense : results){

            if(expense.getExpenseId() == recievedExpenseId)
            {
                currentExpense= expense;
            }
        }

        txtViewName.setText(currentExpense.getName());
        txtViewCategory.setText(currentExpense.getCategory());
        txtViewAmount.setText(String.valueOf(currentExpense.getAmount()));
        txtViewDate.setText(currentExpense.getDate().toString());
    }
}
