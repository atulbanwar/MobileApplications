package com.example.mad.finalexam.DB;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PojoTable {
    static final String TABLE_NAME = "pojo";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_VAR_1 = "var1";
    static final String COLUMN_VAR_2 = "var2";

    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement,");
        sb.append(COLUMN_VAR_1 + " text not null,");
        sb.append(COLUMN_VAR_2 + " date not null");
        sb.append(");");

        try {
            db.execSQL(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        PojoTable.onCreate(db);
    }
}

