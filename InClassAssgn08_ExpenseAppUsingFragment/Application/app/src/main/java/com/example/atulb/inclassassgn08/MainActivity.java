package com.example.atulb.inclassassgn08;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends FragmentActivity implements ExpenseAppFragment.ExpenseAppFragmentInterface, AddExpenseFragment.AddExpenseFragmentInterface, ShowExpensesFragment.ShowExpenseInterface {
    ArrayList<Expense> expenses;
    int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expenses = new ArrayList<>();

        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new ExpenseAppFragment(), "expense_app_fragment").commit();
    }

    @Override
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public void updateExpenses(ArrayList<Expense> expenses) {
        expenses = expenses;
    }

    @Override
    public void goToAddExpense() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new AddExpenseFragment(), "add_expense_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addExpense(Expense expense) {
        expenses.add(expense);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelAddExpense() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void showExpense(int position) {
        selectedItemPosition = position;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, new ShowExpensesFragment(), "show_expense_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Expense getExpense() {
        return expenses.get(selectedItemPosition);
    }

    @Override
    public void closeShowExpense() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }
}