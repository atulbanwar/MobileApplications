package com.example.mad.inclassassgn13.Pojo;
import io.realm.RealmObject;

/**
 * Created by atulb on 10/17/2016.
 */

public class Expense extends RealmObject {
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    private int expenseId;



    private String name;
    private String category;
    private double amount;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }
}