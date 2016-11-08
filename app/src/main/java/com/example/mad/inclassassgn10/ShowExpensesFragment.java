package com.example.mad.inclassassgn10;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowExpensesFragment extends Fragment {
    Expense expense;
    ShowExpensesFragment.ShowExpenseInterface listner;
    TextView txtViewName;
    TextView txtViewCategory;
    TextView txtViewAmount;
    TextView txtViewDate;
    Button btnClose;

    public ShowExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_expenses, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listner = (ShowExpensesFragment.ShowExpenseInterface) getActivity();
        expense = listner.getExpense();

        txtViewName = (TextView) getActivity().findViewById(R.id.text_view_name_value);
        txtViewCategory = (TextView) getActivity().findViewById(R.id.text_view_category_value);
        txtViewAmount = (TextView) getActivity().findViewById(R.id.text_view_amount_value);
        txtViewDate = (TextView) getActivity().findViewById(R.id.text_view_date_value);
        btnClose = (Button) getActivity().findViewById(R.id.button_close);

        txtViewName.setText(expense.getName());
        txtViewCategory.setText(expense.getCategory());
        txtViewAmount.setText(getActivity().getResources().getString(R.string.text_view_expense_cost, String.valueOf(expense.getAmount())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        txtViewDate.setText(dateFormat.format(expense.getDate()));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.closeShowExpense();
            }
        });
    }

    public interface ShowExpenseInterface {
        public Expense getExpense();
        public void closeShowExpense();
    }
}
