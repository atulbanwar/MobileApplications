package com.example.mad.inclassassgn10;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {

    private AddExpenseFragmentInterface listner;

    private EditText edtTxtExpenseName;
    private Spinner spnrCategory;
    private EditText edtTxtAmount;
    private Expense expense;
    private Button addExpenseButton, cancelButton;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listner = (AddExpenseFragmentInterface) getActivity();

        expense = new Expense();
        edtTxtExpenseName = (EditText) getActivity().findViewById(R.id.editTextExpenseName);
        spnrCategory = (Spinner) getActivity().findViewById(R.id.spinnerCategory);
        edtTxtAmount = (EditText) getActivity().findViewById(R.id.editTextAmount);
        addExpenseButton = (Button) getActivity().findViewById(R.id.buttonAddExpense);
        cancelButton = (Button) getActivity().findViewById(R.id.buttonCancel);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = edtTxtExpenseName.getText().toString();
                String category = spnrCategory.getSelectedItem().toString();
                double amount = Double.parseDouble(edtTxtAmount.getText().toString().length() != 0 ? edtTxtAmount.getText().toString() : "0");

                if (expenseName.length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_empty_expense_name), Toast.LENGTH_SHORT).show();
                } else if (category.equals("Select Category")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_empty_category), Toast.LENGTH_SHORT).show();
                } else if (amount == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_empty_amount), Toast.LENGTH_SHORT).show();
                } else {
                    expense.setName(expenseName);
                    expense.setCategory(category);
                    expense.setDate(new Date());
                    expense.setAmount(amount);

                    listner.addExpense(expense);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.cancelAddExpense();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }

    public interface AddExpenseFragmentInterface {
        public void addExpense(Expense expense);
        public void cancelAddExpense();
    }
}
