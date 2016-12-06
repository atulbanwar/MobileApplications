package com.example.mad.inclassassgn13.Pojo;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by atulb on 10/17/2016.
 */

public class Expense extends RealmObject implements Serializable {
    private String name;
    private String category;
    private double amount;
    private Date date;

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
}