package com.assgn.mad.weatherapp.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Homework 06
 * CityTable.java
 * Sanket Patil
 * Atul Kumar Banwar
 */

public class CityTable {
    static final String TABLE_NAME = "favourite_cities";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_CITY_NAME = "name";
    static final String COLUMN_COUNTRY = "country";
    static final String COLUMN_TEMERATURE = "temperature";
    static final String COLUMN_IS_FAVOURITE = "is_favourite";
    static final String COLUMN_DATE = "date";

    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement,");
        sb.append(COLUMN_CITY_NAME + " text not null,");
        sb.append(COLUMN_COUNTRY + " text not null,");
        sb.append(COLUMN_TEMERATURE + " integer not null,");
        sb.append(COLUMN_IS_FAVOURITE + " integer not null,");
        sb.append(COLUMN_DATE + " text not null");
        sb.append(");");

        try {
            db.execSQL(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        CityTable.onCreate(db);
    }
}
