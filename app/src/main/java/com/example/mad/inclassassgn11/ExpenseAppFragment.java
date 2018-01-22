package com.example.mad.inclassassgn11;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseAppFragment extends Fragment {
    ExpenseAppFragmentInterface listner;
    ArrayList<Expense> expenses;
    ExpenseAdapter expenseAdapter;
    ListView lstViewExpenses;
    ImageView imgViewAddExpense;
    TextView txtViewEmptyList;

    public ExpenseAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_app, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listner = (ExpenseAppFragmentInterface) getActivity();
        expenses = new ArrayList<>();
        expenses = listner.getExpenses();

        lstViewExpenses = (ListView) getActivity().findViewById(R.id.list_view_expenses_items);
        imgViewAddExpense = (ImageView) getActivity().findViewById(R.id.image_view_add_expense);
        txtViewEmptyList = (TextView) getActivity().findViewById(R.id.text_view_empty_list);

        expenseAdapter = new ExpenseAdapter(getActivity(), expenses);
        expenseAdapter.setNotifyOnChange(true);
        lstViewExpenses.setAdapter(expenseAdapter);

        if (expenses.size() > 0) {
            txtViewEmptyList.setVisibility(View.INVISIBLE);
        } else {
            txtViewEmptyList.setVisibility(View.VISIBLE);
        }

        lstViewExpenses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                expenseAdapter.remove(expenses.get(position));
                listner.updateExpenses(expenses);

                if (expenses.size() == 0) {
                    txtViewEmptyList.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        lstViewExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listner.showExpense(position);
            }
        });

        imgViewAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.goToAddExpense();
            }
        });
    }

    public interface ExpenseAppFragmentInterface {
        ArrayList<Expense> getExpenses();

        void updateExpenses(ArrayList<Expense> expenses);

        void goToAddExpense();

        void showExpense(int position);
    }
}
