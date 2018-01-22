package com.example.mad.inclassassgn11;

import java.util.ArrayList;

/**
 * Created by atulb on 11/7/2016.
 */

public class UserExpense {
    private String userName;
    private ArrayList<Expense> expenses;

    public UserExpense() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "UserExpense{" +
                "userName='" + userName + '\'' +
                ", expenses=" + expenses +
                '}';
    }
}
