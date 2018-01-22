package com.assgn.mad.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Homework 06
 * DatabaseOpenHelper.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "favourite_cities.db";
    static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CityTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CityTable.onUpgrade(db, oldVersion, newVersion);
    }
}
