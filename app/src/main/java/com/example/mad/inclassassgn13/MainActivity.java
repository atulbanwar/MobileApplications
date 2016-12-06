package com.example.mad.inclassassgn13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mad.inclassassgn13.Adapter.ListViewAdapter;
import com.example.mad.inclassassgn13.Pojo.Expense;
import com.example.mad.inclassassgn13.Pojo.User;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends Activity {
    private Realm realm;
    RealmResults<Expense> expenses;
    ListViewAdapter adapter;

    private TextView textViewEmptyList;

    private Spinner spinnerSortBy;
    private Spinner spinnerFilterBy;

    String[] sortBy;
    String[] filterBy;

    public static final String DETAIL_KEY = "DETAIL_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewEmptyList = (TextView) findViewById(R.id.text_view_empty_list);
        spinnerSortBy = (Spinner) findViewById(R.id.spinner_sort_by);
        spinnerFilterBy = (Spinner) findViewById(R.id.spinner_filter_by_categories);

        sortBy = getResources().getStringArray(R.array.sort_by);
        filterBy = getResources().getStringArray(R.array.filter_by);;

        realm = Realm.getDefaultInstance();

        /*
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Expense expense = realm.createObject(Expense.class);
                expense.setName("AAA");
                expense.setAmount(100);
                expense.setDate(Calendar.getInstance().getTime().toString());
                expense.setCategory("Groceries");

                expense = realm.createObject(Expense.class);
                expense.setName("CCC");
                expense.setAmount(300);
                expense.setDate(Calendar.getInstance().getTime().toString());
                expense.setCategory("Invoice");

                expense = realm.createObject(Expense.class);
                expense.setName("BBB");
                expense.setAmount(200);
                expense.setDate(Calendar.getInstance().getTime().toString());
                expense.setCategory("Trips");

                expense = realm.createObject(Expense.class);
                expense.setName("KKK");
                expense.setAmount(280);
                expense.setDate(Calendar.getInstance().getTime().toString());
                expense.setCategory("Trips");

                expense = realm.createObject(Expense.class);
                expense.setName("FFF");
                expense.setAmount(210);
                expense.setDate(Calendar.getInstance().getTime().toString());
                expense.setCategory("Utilities");
            }
        });
        */

        expenses = realm.where(Expense.class).findAll();

        if (expenses.size() > 0) {
            textViewEmptyList.setVisibility(View.INVISIBLE);
        } else {
            textViewEmptyList.setVisibility(View.VISIBLE);
        }

        adapter = new ListViewAdapter(this, expenses);

        final ListView listView = (ListView) findViewById(R.id.list_view_expenses_items);
        listView.setAdapter(adapter);

        expenses.addChangeListener(new RealmChangeListener<RealmResults<Expense>>() {
            @Override
            public void onChange(RealmResults<Expense> element) {
                if (element.size() > 0) {
                    textViewEmptyList.setVisibility(View.INVISIBLE);
                } else {
                    textViewEmptyList.setVisibility(View.VISIBLE);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ShowExpenseActivity.class);
                intent.putExtra("EXPENSE_SHOW", expense.getExpenseId());
                startActivity(intent);
            }
        });

        /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String expenseName = adapter.getItem(i).getName();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(Expense.class).equalTo("name", expenseName).findAll().deleteAllFromRealm();
                    }
                });
                return true;
            }
        });
        */

        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortByItem = sortBy[position];
                expenses = expenses.sort(sortByItem);

                adapter.updateData(expenses);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFilterBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filterByItem = filterBy[position];

                if (filterByItem.equals("Show All")) {
                    expenses = realm.where(Expense.class).findAll();
                } else {
                    expenses = realm.where(Expense.class).equalTo("category", filterByItem).findAll();
                }

                adapter.updateData(expenses);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void goToAddExpense(View view) {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        startActivity(intent);
    }
}
