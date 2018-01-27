package com.example.mad.midterm;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by atulb on 10/24/2016.
 */

public class AppTable {
    static final String TABLE_NAME = "apps";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_APP_NAME = "app_name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_URL = "url";

    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement,");
        sb.append(COLUMN_APP_NAME + " text not null,");
        sb.append(COLUMN_PRICE + " integer not null,");
        sb.append(COLUMN_URL + " text not null");
        sb.append(");");

        try {
            db.execSQL(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        AppTable.onCreate(db);
    }
}
