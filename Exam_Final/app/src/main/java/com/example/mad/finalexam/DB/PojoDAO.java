package com.example.mad.finalexam.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PojoDAO {
    private SQLiteDatabase db;

    public PojoDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(PojoForDB pojo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PojoTable.COLUMN_VAR_1, pojo.getVariable1());
        contentValues.put(PojoTable.COLUMN_VAR_2, pojo.getVariable2());
        return db.insert(PojoTable.TABLE_NAME, null, contentValues);
    }

    public boolean update(PojoForDB pojo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PojoTable.COLUMN_VAR_1, pojo.getVariable1());
        contentValues.put(PojoTable.COLUMN_VAR_2, pojo.getVariable2());
        return db.update(PojoTable.TABLE_NAME, contentValues, PojoTable.COLUMN_ID + "=?", new String[]{pojo.getId() + ""}) > 0;
    }

    public boolean delete(PojoForDB pojo) {
        return db.delete(PojoTable.TABLE_NAME, PojoTable.COLUMN_ID + "=?", new String[]{pojo.getId() + ""}) > 0;
    }

    public PojoForDB get(long id) {
        PojoForDB pojo = null;
        Cursor c = db.query(true, PojoTable.TABLE_NAME,
                new String[]{PojoTable.COLUMN_ID, PojoTable.COLUMN_VAR_1, PojoTable.COLUMN_VAR_2},
                PojoTable.COLUMN_ID + "=?",
                new String[]{id + ""},
                null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            pojo = buildPojoFromCursor(c);
            c.close();
        }
        return pojo;
    }

    public ArrayList getAll() {
        ArrayList pojos = new ArrayList<PojoForDB>();
        Cursor c = db.query(PojoTable.TABLE_NAME,
                new String[]{PojoTable.COLUMN_ID, PojoTable.COLUMN_VAR_1, PojoTable.COLUMN_VAR_2},
                null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                PojoForDB pojo = buildPojoFromCursor(c);
                if (pojo != null) {
                    pojos.add(pojo);
                }
            } while (c.moveToNext());
        }
        return pojos;
    }

    public PojoForDB get(String var1, String var2) {
        PojoForDB pojo = null;
        Cursor c = db.query(true, PojoTable.TABLE_NAME,
                new String[]{PojoTable.COLUMN_ID, PojoTable.COLUMN_VAR_1, PojoTable.COLUMN_VAR_2},
                PojoTable.COLUMN_VAR_1 + "=? AND " + PojoTable.COLUMN_VAR_2 + "=?",
                new String[]{var1, var2},
                null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            pojo = buildPojoFromCursor(c);
            c.close();
        }
        return pojo;
    }

    private PojoForDB buildPojoFromCursor(Cursor c) {
        PojoForDB pojo = null;
        if (c != null) {
            pojo = new PojoForDB();
            pojo.setId(c.getLong(0));
            pojo.setVariable1(c.getString(1));
            pojo.setVariable2(c.getString(2));
        }

        return pojo;
    }
}