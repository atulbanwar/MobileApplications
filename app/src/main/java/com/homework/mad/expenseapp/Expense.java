package com.homework.mad.expenseapp;

import android.net.Uri;

import java.util.Date;

/**
 * Home Work 2
 * Sanket Patil
 * Atul Kumar Banwar
 */
public class Expense {
    private String name;
    private String category;
    private double amount;
    private Date date;
    private Uri receipt;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Uri getReceipt() {
        return receipt;
    }

    public void setReceipt(Uri receipt) {
        this.receipt = receipt;
    }
}
