package com.example.mad.midterm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atulb on 10/24/2016.
 */

public class AppDao {
    private SQLiteDatabase db;

    public AppDao (SQLiteDatabase db) {
        this.db = db;
    }

    public long save(App app) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppTable.COLUMN_APP_NAME, app.getName());
        contentValues.put(AppTable.COLUMN_PRICE, app.getPrice());
        contentValues.put(AppTable.COLUMN_URL, app.getImageUrl());
        return db.insert(AppTable.TABLE_NAME, null, contentValues);
    }

    public boolean delete(App app) {
        return db.delete(AppTable.TABLE_NAME, AppTable.COLUMN_APP_NAME + "=?", new String[]{app.getName() + ""}) > 0;
    }

    public List<App> getAll() {
        List<App> apps = new ArrayList<App>();
        Cursor c = db.query(AppTable.TABLE_NAME,
                new String[]{AppTable.COLUMN_ID, AppTable.COLUMN_APP_NAME, AppTable.COLUMN_PRICE, AppTable.COLUMN_URL},
                null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                App app = buildAppFromCursor(c);
                if (app != null) {
                    apps.add(app);
                }
            } while (c.moveToNext());
        }
        return apps;
    }

    private App buildAppFromCursor(Cursor c) {
        App app = null;
        if (c != null) {
            app = new App();
            app.setName(c.getString(1));
            app.setPrice(c.getInt(2));
            app.setImageUrl(c.getString(3));
        }
        return app;
    }
}
